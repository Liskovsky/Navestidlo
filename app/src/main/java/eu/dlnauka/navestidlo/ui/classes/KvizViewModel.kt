package eu.dlnauka.navestidlo.ui.classes

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import eu.dlnauka.navestidlo.ui.datastore.Event
import eu.dlnauka.navestidlo.ui.datastore.NavestiRepository
import eu.dlnauka.navestidlo.ui.components.AllBlinkingColors

// Definice třídy KvizViewModel, která dědí funkcionality od třídy ViewModel
class KvizViewModel(
    private val repository: NavestiRepository) : ViewModel() {

    // Deklarace a inicializace proměnných
    val isKvizHeadSignalBoxVisible = mutableStateOf(true)
    private val isAllShiftBoxVisible = mutableStateOf(false)
    private val shiftColors = mutableStateOf(
        listOf<AllBlinkingColors>(
            AllBlinkingColors.SolidColor(Color.DarkGray),
            AllBlinkingColors.SolidColor(Color.DarkGray)
        )
    )
    val speedLinesColors = mutableStateOf(
        listOf<AllBlinkingColors>(
            AllBlinkingColors.SolidColor(Color.DarkGray),
            AllBlinkingColors.SolidColor(Color.DarkGray)
        )
    )
    val isKvizSpeedLinesVisible = mutableStateOf(true)
    val signalColors = mutableStateOf(
        listOf<AllBlinkingColors>(
            AllBlinkingColors.SolidColor(Color.DarkGray),
            AllBlinkingColors.SolidColor(Color.DarkGray),
            AllBlinkingColors.SolidColor(Color.DarkGray),
            AllBlinkingColors.SolidColor(Color.DarkGray),
            AllBlinkingColors.SolidColor(Color.DarkGray)
        )
    )
    private val isShowLinesVisible = mutableStateOf(false)
    val isAllTab120Visible = mutableStateOf(false)
    val onAllTabNumberText = mutableStateOf("")
    val isAllTabNumberVisible = mutableStateOf(false)
    val onAllTabPictureText = mutableStateOf("")
    val isAllTabPictureVisible = mutableStateOf(false)
    val fineMessage = mutableStateOf<String?>(null)
    val errorMessage = mutableStateOf<String?>(null)
    val resultCheck = mutableStateOf<String?>(null)
    val assignment = mutableStateOf("Načítám...")
    val eventId = mutableStateOf("")
    val isAllDescriptionBoxVisible = mutableStateOf(false)
    val resetTrigger = mutableIntStateOf(0)

    // Asynchronní funkce pro načtení náhodné události
    suspend fun loadRandomEvent() {
        resetNavestidlo()
        try {
            var randomEventDocument = repository.getRandomEventDocument()
            var attempts = 5

            // Pokračuje v pokusech načíst náhodnou událost, dokud není nalezena nebo dokud nedojdou pokusy (ochrana proti zacyklení)
            while (randomEventDocument != null && attempts-- > 0) {
                val randomEvent = repository.getEvent(randomEventDocument.id)

                // Zkontroluje, jestli je nalezena platná událost a nastaví hodnoty, je zde vyloučeno nastavení hodnot pro některé události
                if (randomEvent != null &&
                    !randomEventDocument.id.equals("PosunDovolenPN", ignoreCase = true) &&
                    !randomEventDocument.id.equals("PosunZakazanPN", ignoreCase = true) &&
                    !randomEventDocument.id.equals("PosunDovolenHLN", ignoreCase = true)) {

                    eventId.value = randomEventDocument.id
                    assignment.value = randomEvent.name
                    return
                }
                randomEventDocument = repository.getRandomEventDocument()
            }
            assignment.value = "Událost nebyla nalezena"
        } catch (e: Exception) {
            assignment.value = "Chyba při načítání"
        }
    }
    // Funkce resetující stav návěstidla
    fun resetNavestidlo() {
        val newState = KvizScreenState(
            isAllTab120Visible = false,
            isAllTabNumberVisible = false,
            isAllTabPictureVisible = false,
            isKvizHeadSignalBoxVisible = true,
            isKvizSpeedLinesVisible = true,
            speedLinesColors = listOf(
                AllBlinkingColors.SolidColor(Color.DarkGray),
                AllBlinkingColors.SolidColor(Color.DarkGray)
            ),
            signalColors = listOf(
                AllBlinkingColors.SolidColor(Color.DarkGray),
                AllBlinkingColors.SolidColor(Color.DarkGray),
                AllBlinkingColors.SolidColor(Color.DarkGray),
                AllBlinkingColors.SolidColor(Color.DarkGray),
                AllBlinkingColors.SolidColor(Color.DarkGray)
            )
        )
        // Nastavení proměnných podle nového stavu
        isKvizHeadSignalBoxVisible.value = newState.isKvizHeadSignalBoxVisible
        isAllShiftBoxVisible.value = false
        speedLinesColors.value = newState.speedLinesColors
        signalColors.value = newState.signalColors
        isShowLinesVisible.value = false
        isKvizSpeedLinesVisible.value = newState.isKvizSpeedLinesVisible
        isAllTab120Visible.value = newState.isAllTab120Visible
        onAllTabNumberText.value = ""
        isAllTabNumberVisible.value = newState.isAllTabNumberVisible
        onAllTabPictureText.value = ""
        isAllTabPictureVisible.value = newState.isAllTabPictureVisible
        resetTrigger.intValue += 1
    }
    // Funkce zajišťující kontrolu nastavení uživatelova eventu a porovnání s očekávaným nastavením eventu dle databáze
    fun checkSettings (expectedEvent: Event?) {
        if (expectedEvent == null) {
            resultCheck.value = "Událost nebyla načtena."
            return
        }
        val userEvent = createUserEvent()
        val errors = mutableListOf<String>()

        // Pomocná funkce pro kontrolu viditelnosti a porovnání hodnot, kde vylučuji zachycování chyb u prvků kde visible = false
        fun checkVisibilityAndCompare(
            isExpectedVisible: Boolean?,
            isUserVisible: Boolean,
            expectedValue: Any,
            userValue: Any,
            errorMessage: String
        ) {
            if (isExpectedVisible == true) {
                if (expectedValue is List<*> && userValue is List<*>) {
                    val expectedColors = expectedValue.map { it as? AllBlinkingColors }
                    val userColors = userValue.map { it as? AllBlinkingColors }
                    if (expectedColors != userColors) {
                        errors.add(errorMessage)
                    }
                } else if (expectedValue != userValue) {
                    errors.add(errorMessage)
                }
            } else if (isExpectedVisible != isUserVisible) {
                errors.add(errorMessage)
            }
        }
        // Volání pomocné funkce pro porovnání jednotlivých komponent návěstidla
        checkVisibilityAndCompare(
            expectedEvent.isAllHeadSignalVisible,
            userEvent.isAllHeadSignalVisible,
            expectedEvent.signalColors,
            userEvent.signalColors,
            "Barvy návěstidla jsou chybně."
        )
        checkVisibilityAndCompare(
            expectedEvent.isAllSpeedLinesVisible,
            userEvent.isAllSpeedLinesVisible,
            expectedEvent.speedLinesColors,
            userEvent.speedLinesColors,
            "Rychlostní pruhy jsou chybně."
        )
        checkVisibilityAndCompare(
            expectedEvent.isAllTabNumberVisible,
            userEvent.isAllTabNumberVisible,
            expectedEvent.allTabNumberText,
            userEvent.allTabNumberText,
            "Rychlost na indikátoru je chybně."
        )
        checkVisibilityAndCompare(
            expectedEvent.isAllTabPictureVisible,
            userEvent.isAllTabPictureVisible,
            expectedEvent.allTabPictureText,
            userEvent.allTabPictureText,
            "Doplňková tabulka je chybně."
        )
        checkVisibilityAndCompare(
            expectedEvent.isAllShiftBoxVisible,
            userEvent.isAllShiftBoxVisible,
            expectedEvent.shiftColors,
            userEvent.shiftColors,
            "Barvy boxu posunu se neshodují."
        )
        checkVisibilityAndCompare(
            expectedEvent.isAllTab120Visible,
            userEvent.isAllTab120Visible,
            expectedEvent.isAllTab120Visible,
            userEvent.isAllTab120Visible,
            "Indikátor 120 km/h je chybně."
        )
        // Pokud nejsou nalezeny chyby v seznamu, nastaví se zpráva s úspěšným zakončení, jinak se nastaví zpráva se sezname chyb
        if (errors.isEmpty()) {
            val successMessages = listOf(
                "Jen tak dále :-D.",
                "Výborně, z Tebe už je hotovej fíra!",
                "Ty už jezdíš hodně dlouho, co?",
                "Udělej i nějakou chybu! Přestává to být sranda.",
                "Ty máš paměť jako slon. :-D"
            )
            val randomMessage = successMessages.random()
            resultCheck.value = "✅ USPĚL ✅"
            fineMessage.value = randomMessage
        } else {
            val errorText = errors.joinToString("\n")
            resultCheck.value = "❌ NEUSPĚL ❌"
            errorMessage.value = errorText
        }
        isAllDescriptionBoxVisible.value = true
    }
    // Funkce zajišťující vytvoření uživatelského eventu - nastavení návěstidla uživatelem
    private fun createUserEvent(): Event {
        val userEvent = Event(
            description = "",
            name = assignment.value,
            isAllTab120Visible = isAllTab120Visible.value,
            isAllHeadSignalVisible = isKvizHeadSignalBoxVisible.value,
            isShowLinesVisible = isShowLinesVisible.value,
            signalColors = signalColors.value,
            isAllSpeedLinesVisible = isKvizSpeedLinesVisible.value,
            speedLinesColors = speedLinesColors.value,
            isAllTabNumberVisible = isAllTabNumberVisible.value,
            allTabNumberText = onAllTabNumberText.value,
            isAllTabPictureVisible = isAllTabPictureVisible.value,
            allTabPictureText = onAllTabPictureText.value,
            isAllShiftBoxVisible = isAllShiftBoxVisible.value,
            shiftColors = shiftColors.value
        )
        return userEvent
    }
}
