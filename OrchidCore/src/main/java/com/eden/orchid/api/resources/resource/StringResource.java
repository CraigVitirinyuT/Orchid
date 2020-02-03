package com.eden.orchid.api.resources.resource;

import com.eden.common.json.JSONElement;
import com.eden.common.util.EdenPair;
import com.eden.orchid.api.OrchidContext;
import com.eden.orchid.api.theme.pages.OrchidReference;
import com.eden.orchid.utilities.OrchidExtensionsKt;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Map;

/**
 * A Resource type that provides a plain String as content to a template. When used with renderTemplate() or renderString(),
 * this resource will supply the `page.content` variable to the template renderer. When used with renderRaw(), the raw
 * plain String content will be written directly instead.
 */
/**
 * A Resource type that provides a plain String as content to a template. When used with renderTemplate() or renderString(),
 * this resource will supply the `page.content` variable to the template renderer. When used with renderRaw(), the raw
 * plain String content will be written directly instead.
 */
public final class StringResource extends OrchidResource {

    private final String hardcodedString;
    private final JSONElement hardcodedData;
    public StringResource(String content, OrchidReference reference, JSONElement data) {
        super(reference);
        this.hardcodedString = (content != null) ? content : "";
        this.hardcodedData = (data != null) ? data : new JSONElement(new JSONObject());
    }

    @NotNull
    @Override
    public InputStream getContentStream() {
        return OrchidExtensionsKt.asInputStream(hardcodedString);
    }

    @Override
    public JSONElement getEmbeddedData() {
        return OrchidExtensionsKt.merge(hardcodedData, super.getEmbeddedData());
    }
}
