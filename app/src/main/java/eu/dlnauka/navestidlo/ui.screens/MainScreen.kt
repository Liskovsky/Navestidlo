package eu.dlnauka.navestidlo.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.dlnauka.navestidlo.ui.datastore.NavestiRepository
import eu.dlnauka.navestidlo.ui.localization.LanguagePreferenceManager
import eu.dlnauka.navestidlo.ui.localization.LocalLocalizedContext
import eu.dlnauka.navestidlo.ui.localization.wrapWithLocale

object Destinations {
    const val TRENAZER = "Trenažer"
    const val KVIZ = "Kvíz"
    const val TEST = "Test"
}

@Composable
fun MainScreen(
    repository: NavestiRepository,
    onExitApp: () -> Unit
) {
    // Navigation controller
    val navController = rememberNavController()

    // Current context and resolved language
    val context = LocalContext.current
    val langCode by produceState(initialValue = "cs") {
        value = LanguagePreferenceManager.resolveAppLanguage(context)
    }

    // Context wrapped with selected locale
    val localizedContext = context.wrapWithLocale(langCode)

    CompositionLocalProvider(
        LocalLocalizedContext provides localizedContext,
        LocalContext provides localizedContext
    ) {
        NavHost(
            navController = navController,
            startDestination = Destinations.TRENAZER
        ) {
            composable(Destinations.TRENAZER) {
                Trenazer(
                    navController = navController,
                    repository = repository,
                    onExitApp = onExitApp
                )
            }
            composable(Destinations.KVIZ) {
                Kviz(
                    navController = navController,
                    onExitApp = onExitApp,
                    repository = repository
                )
            }
            composable(Destinations.TEST) {
                Test(
                    navController = navController,
                    onExitApp = onExitApp,
                    repository = repository
                )
            }
        }
    }
}
