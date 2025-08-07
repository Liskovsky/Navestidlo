package eu.dlnauka.navestidlo.ui.classes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import eu.dlnauka.navestidlo.ui.datastore.NavestiRepository
import eu.dlnauka.navestidlo.ui.screens.MainScreen

// Definuje třídu TrenazerActivity jako podtřídu ComponentActivity
class TrenazerActivity : ComponentActivity() {

    // Přepíše metodu onCreate, která se zavolá při vytvoření aktivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Definice výrazu pro ukončení aplikace
        val onExitApp: () -> Unit = {
            finish()
        }
        // Nastaví obsah aktivity na hlavní obrazovku kde předá repository a funkci pro ukončení aplikace
        setContent {
            MainScreen(repository = NavestiRepository(), onExitApp = onExitApp)
        }
    }
}
