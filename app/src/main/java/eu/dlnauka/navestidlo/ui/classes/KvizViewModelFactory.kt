package eu.dlnauka.navestidlo.ui.classes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eu.dlnauka.navestidlo.ui.datastore.NavestiRepository

// Definuje třídu pro vytváření instancí ViewModelu
class KvizViewModelFactory(private val repository: NavestiRepository) : ViewModelProvider.Factory {

    // Přepisuje metodu create, která vytvoří ViewModel
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        // Zkontroluje, zda modelClass je KvizViewModel
        if (modelClass.isAssignableFrom(KvizViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")

            // Vrátí novou instanci KvizViewModel s předaným repository
            return KvizViewModel(repository) as T
        }
        // Pokud modelClass není KvizViewModel, vyhodí výjimku
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
