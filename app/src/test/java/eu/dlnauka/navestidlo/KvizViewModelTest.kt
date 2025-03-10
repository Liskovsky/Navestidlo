package eu.dlnauka.navestidlo

import androidx.compose.ui.graphics.Color
import com.google.firebase.firestore.DocumentSnapshot
import eu.dlnauka.navestidlo.ui.classes.KvizViewModel
import eu.dlnauka.navestidlo.ui.components.AllBlinkingColors
import eu.dlnauka.navestidlo.ui.datastore.Event
import eu.dlnauka.navestidlo.ui.datastore.NavestiRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class KvizViewModelTest {

    private lateinit var mockRepository: NavestiRepository
    private lateinit var kvizViewModel: KvizViewModel

    @Before
    fun setup() {
        println("Inicializace: Nastavuji mock NavestiRepository a KvizViewModel...")
        mockRepository = mock()
        kvizViewModel = KvizViewModel(mockRepository)
        println("Inicializace dokončena: Mock repository a ViewModel připraveny.")
    }

    @Test
    fun testLoadRandomEvent(): Unit = runBlocking {
        println("Začátek testu: testLoadRandomEvent")

        // Mockování výstupu metody getRandomEventDocument
        val mockDocument: DocumentSnapshot = mock()
        println("Vytvoření mock dokumentu pro getRandomEventDocument.")
        `when`(mockRepository.getRandomEventDocument()).thenReturn(mockDocument)
        println("Mockovaná metoda getRandomEventDocument nastavena na vrácení mockDocument.")

        // Volání metody loadRandomEvent z ViewModelu
        println("Volání metody loadRandomEvent...")
        kvizViewModel.loadRandomEvent()
        println("Metoda loadRandomEvent úspěšně volána.")

        // Ověření, že metoda getRandomEventDocument byla volána
        println("Ověřuji, že mockovaná metoda getRandomEventDocument byla volána...")
        verify(mockRepository).getRandomEventDocument()
        println("Ověření úspěšné: Metoda getRandomEventDocument byla volána.")

        // Potvrzení dokončení testu
        println("Test testLoadRandomEvent úspěšně dokončen.")
    }

    @Test
    fun testCheckSettings_WrongAnswer() {
        println("Začátek testu: testCheckSettings_WrongAnswer")

        // Vytvoření instance události, která bude použita jako očekávaný vstup
        val expectedEvent = Event(
            name = "Test",
            signalColors = listOf(AllBlinkingColors.SolidColor(Color.Green))
        )
        println("Vytvoření očekávané události: ${expectedEvent.name} s barvami ${expectedEvent.signalColors}")

        // Volání metody checkSettings s očekávanou událostí
        println("Volání metody checkSettings...")
        kvizViewModel.checkSettings(expectedEvent)
        println("Metoda checkSettings úspěšně volána.")

        // Ověření výsledku - očekáváme, že výsledek bude "❌ NEUSPĚL ❌"
        val actualResult = kvizViewModel.resultCheck.value
        println("Kontrola výsledku: Očekávaný výsledek = \"❌ NEUSPĚL ❌\", Skutečný výsledek = \"$actualResult\"")
        assertEquals("❌ NEUSPĚL ❌", actualResult)

        // Potvrzení dokončení testu
        println("Test testCheckSettings_WrongAnswer úspěšně dokončen.")
    }
}
