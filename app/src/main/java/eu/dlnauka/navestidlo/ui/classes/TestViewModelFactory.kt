package eu.dlnauka.navestidlo.ui.classes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eu.dlnauka.navestidlo.ui.datastore.NavestiRepository

// Definuje třídu pro vytváření instancí ViewModelu
class TestViewModelFactory(private val repository: NavestiRepository) : ViewModelProvider.Factory {

    // Přepisuje metodu create, která vytvoří ViewModel
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        // Zkontroluje, zda modelClass je TestViewModel
        if (modelClass.isAssignableFrom(TestViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")

            // Vrátí novou instanci TestViewModel s předaným repository
            return TestViewModel(repository) as T
        }
        // Pokud modelClass není TestViewModel, vyhodí výjimku
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
