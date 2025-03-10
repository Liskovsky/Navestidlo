package eu.dlnauka.navestidlo.ui.classes

import eu.dlnauka.navestidlo.ui.components.AllBlinkingColors

// Definice datové třídy KvizScreenState pro zajištění stavu obrazovky
data class KvizScreenState(
    var isAllTab120Visible: Boolean = false,
    var isAllTabNumberVisible: Boolean = false,
    var isAllTabPictureVisible: Boolean = false,
    var isKvizHeadSignalBoxVisible: Boolean = true,
    var isKvizSpeedLinesVisible: Boolean = true,
    val signalColors: List<AllBlinkingColors> = emptyList(),
    val speedLinesColors: List<AllBlinkingColors> = emptyList(),
    var currentAllTabNumberIndex: Int = -1,
    var currentAllTabPictureIndex: Int = -1
) {
    // Vytvoření seznamů pro procházení komponent
    val numbers = listOf("3", "5", "12")
    val pictures = listOf("oddil", "skupina", "vjezd", "stanoviste", "predvest")

    // Funkce pro zobrazení dalšího čísla tabulky
    fun showNextAllTabNumber(): KvizScreenState {
        val newIndex = if (currentAllTabNumberIndex == numbers.size - 1) -1 else currentAllTabNumberIndex + 1
        return copy(
            isAllTabPictureVisible = false,
            currentAllTabNumberIndex = newIndex,
            isAllTabNumberVisible = newIndex != -1,
            isKvizSpeedLinesVisible = newIndex == -1
        )
    }
    // Funkce pro zobrazení dalšího obrázku tabulky
    fun showNextAllTabPicture(): KvizScreenState {
        val newIndex = if (currentAllTabPictureIndex == pictures.size - 1) -1 else currentAllTabPictureIndex + 1
        return copy(
            isAllTabNumberVisible = false,
            currentAllTabPictureIndex = newIndex,
            isAllTabPictureVisible = newIndex != -1,
            isKvizSpeedLinesVisible = newIndex == -1
        )
    }
    // Funkce pro resetování čísel tabulek
    fun resetAllTabNumber(): KvizScreenState {
        return copy(
            currentAllTabNumberIndex = -1,
            isAllTabNumberVisible = false,
            isKvizSpeedLinesVisible = true
        )
    }
    // Funkce pro resetování obrázků tabulek
    fun resetAllTabPicture(): KvizScreenState {
        return copy(
            currentAllTabPictureIndex = -1,
            isAllTabPictureVisible = false,
            isKvizSpeedLinesVisible = true
        )
    }
}
