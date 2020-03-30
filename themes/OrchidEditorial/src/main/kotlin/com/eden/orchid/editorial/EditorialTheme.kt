package com.eden.orchid.editorial

import com.caseyjbrooks.clog.Clog
import com.eden.orchid.api.OrchidContext
import com.eden.orchid.api.options.annotations.Archetype
import com.eden.orchid.api.options.annotations.BooleanDefault
import com.eden.orchid.api.options.annotations.Description
import com.eden.orchid.api.options.annotations.Option
import com.eden.orchid.api.options.annotations.StringDefault
import com.eden.orchid.api.options.archetypes.ConfigArchetype
import com.eden.orchid.api.theme.Theme
import com.eden.orchid.api.theme.models.Social
import javax.inject.Inject

@Description("A theme based on Editorial by HTML5Up, with nested navigation menus good for building wikis.",
        name = "Editorial"
)
@Archetype(value = ConfigArchetype::class, key = "Editorial")
class EditorialTheme
@Inject
constructor(
        context: OrchidContext
) : Theme(context, "Editorial") {

    @Option
    @StringDefault("#f56a6a")
    @Description("The CSS HEX value for the site's primary color.")
    lateinit var primaryColor: String

    @Option
    @Description("Your social media links.")
    var social: Social? = null

    override fun loadAssets() {
        addCss("assets/css/editorial_main.scss")
        addCss("assets/css/editorial_orchidCustomizations.scss")
        addCss("assets/css/orchidSearch.scss")

        addJs("https://cdnjs.cloudflare.com/ajax/libs/jquery/1.11.3/jquery.min.js")
        addJs("https://cdnjs.cloudflare.com/ajax/libs/skel/3.0.1/skel.min.js")
        addJs("assets/js/editorial_util.js")
        addJs("assets/js/editorial_main.js")
        addJs("assets/js/editorial_orchidCustomizations.js")
    }
}
