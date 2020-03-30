package com.eden.orchid.impl.compilers.pebble.tag;

import com.eden.orchid.api.OrchidContext;
import com.eden.orchid.api.compilers.TemplateTag;
import com.eden.orchid.api.theme.pages.OrchidPage;
import com.eden.orchid.impl.compilers.pebble.PebbleWrapperTemplateTag;
import javax.inject.Provider;
import com.mitchellbosecke.pebble.error.ParserException;
import com.mitchellbosecke.pebble.lexer.Token;
import com.mitchellbosecke.pebble.lexer.TokenStream;
import com.mitchellbosecke.pebble.node.RenderableNode;
import com.mitchellbosecke.pebble.node.expression.Expression;
import com.mitchellbosecke.pebble.parser.Parser;
import com.mitchellbosecke.pebble.template.EvaluationContextImpl;
import com.mitchellbosecke.pebble.template.PebbleTemplateImpl;
import com.mitchellbosecke.pebble.utils.StringUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

public class ContentTagParser extends BaseTagParser {

    private final Class<? extends TemplateTag> tagClass;
    private final String[] parameters;

    private Map<String, Expression<?>> paramExpressionMap;
    private Expression<?> tagBodyExpression;

    public ContentTagParser(
            Provider<OrchidContext> contextProvider,
            String name,
            String[] parameters,
            Class<? extends TemplateTag> tagClass) {
        super(contextProvider, name);
        this.parameters = parameters;
        this.tagClass = tagClass;
    }

    public RenderableNode parse(Token token, Parser parser) throws ParserException {
        TokenStream stream = parser.getStream();
        int lineNumber = token.getLineNumber();

        // skip over the tag name token
        stream.next();

        // parameter expressions will be added here
        paramExpressionMap = parseParams(parameters, true, tagClass, stream, parser);
        List<Expression<?>> bodyFilters = parseBodyFilters(stream, parser);
        stream.expect(Token.Type.EXECUTE_END);
        tagBodyExpression = parseBody(bodyFilters, name, stream, parser);

        return new PebbleWrapperTemplateTag.TemplateTagNode(lineNumber, this);
    }

    @Override
    public void render(PebbleTemplateImpl self, Writer writer, EvaluationContextImpl context) throws IOException {
        OrchidContext orchidContext = contextProvider.get();
        TemplateTag freshTag = orchidContext.resolve(tagClass);
        Map<String, Object> evaluatedParamExpressionMap = evaluateParams(paramExpressionMap, self, context);

        Object pageVar = context.getVariable("page");
        final OrchidPage actualPage;
        if (pageVar instanceof OrchidPage) {
            actualPage = (OrchidPage) pageVar;
        }
        else {
            actualPage = null;
        }

        freshTag.extractOptions(orchidContext, evaluatedParamExpressionMap);

        String bodyContent = StringUtils.toString(tagBodyExpression.evaluate(self, context)).trim();
        TemplateTag.Tab tab = new TemplateTag.SimpleTab(null, null, bodyContent);
        freshTag.setContent(tab);

        freshTag.onRender(orchidContext, actualPage);
        if (freshTag.rendersContent()) {
            writer.append(freshTag.renderContent(orchidContext, actualPage));
        }
    }
}
