package com.eden.orchid.writersblocks.functions

import com.eden.orchid.api.OrchidContext
import com.eden.orchid.api.compilers.TemplateFunction
import com.eden.orchid.api.converters.StringConverter
import com.eden.orchid.api.options.annotations.Description
import com.eden.orchid.api.options.annotations.Option
import com.eden.orchid.api.theme.pages.OrchidPage
import com.eden.orchid.utilities.nl2br
import com.eden.orchid.utilities.resolve

@Description("Convert newline characters to `<br>` tags.", name = "nl2br")
class Nl2brFunction : TemplateFunction("nl2br", true) {

    @Option
    @Description("The input to encode.")
    lateinit var input: String

    override fun parameters() = arrayOf(::input.name)

    override fun apply(context: OrchidContext, page: OrchidPage?): Any? {
        return context
            .resolve<StringConverter>()
            .convert(String::class.java, input)
            .second
            .nl2br()
    }
}
