package eu.dlnauka.navestidlo.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.dlnauka.navestidlo.ui.datastore.NavestiRepository

object Destinations {
    const val TRENAZER = "Trenažer"
    const val KVIZ = "Kvíz"
    const val TEST = "Test"
}
@Composable
fun MainScreen(repository: NavestiRepository, onExitApp: () -> Unit) {

    // Inicializace navigace mezi obrazovkami
    val navController = rememberNavController()

    // Definování dostupných obrazovek v aplikaci
    NavHost(navController = navController, startDestination = Destinations.TRENAZER) {
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
