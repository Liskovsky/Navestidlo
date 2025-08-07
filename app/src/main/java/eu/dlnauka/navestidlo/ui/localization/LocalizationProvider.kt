package eu.dlnauka.navestidlo.ui.localization

import android.content.Context
import androidx.compose.runtime.compositionLocalOf

val LocalLocalizedContext = compositionLocalOf<Context> {
    error("No localized context provided")
}
