package eu.dlnauka.navestidlo.ui.utils

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import eu.dlnauka.navestidlo.ui.localization.LocalLocalizedContext
import java.util.Locale

fun Map<String, String>.localized(defaultLang: String = "cs"): String {
    val currentLang = Locale.getDefault().language
    return this[currentLang] ?: this[defaultLang] ?: values.firstOrNull() ?: ""
}

@Composable
fun localizedString(@StringRes id: Int): String {
    val ctx = LocalLocalizedContext.current
    return remember(ctx, id) { ctx.getString(id) }
}
@Composable
fun localizedString(@StringRes id: Int, vararg args: Any): String {
    val ctx = LocalLocalizedContext.current
    return remember(ctx, id, *args) { ctx.getString(id, *args) }
}
