@file:Suppress("DEPRECATION")

package eu.dlnauka.navestidlo.ui.localization

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import java.util.*

fun Context.wrapWithLocale(language: String): ContextWrapper {
    val locale = Locale(language)
    Locale.setDefault(locale)
    val config = Configuration(resources.configuration)
    config.setLocale(locale)
    config.setLayoutDirection(locale)

    val newContext =
        createConfigurationContext(config)

    return ContextWrapper(newContext)
}
