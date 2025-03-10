package eu.dlnauka.navestidlo

import com.google.firebase.firestore.DocumentSnapshot
import eu.dlnauka.navestidlo.ui.datastore.Event
import eu.dlnauka.navestidlo.ui.datastore.NavestiDataSource
import eu.dlnauka.navestidlo.ui.datastore.NavestiRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.kotlin.mock
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class NavestiRepositoryTest {

    private lateinit var mockDataSource: NavestiDataSource
    private lateinit var repository: NavestiRepository

    @Before
    fun setup() {
        println("Inicializace mocků a repository...")
        mockDataSource = mock()
        repository = NavestiRepository(mockDataSource)
        println("Setup dokončen.")
    }

    @Test
    fun testGetNavestiList() = runBlocking {
        println("Začátek testu: testGetNavestiList")

        // Mockování chování metody getNavestiList v dataSource
        val mockDocuments = listOf(
            Pair("id1", "Návěst 1"),
            Pair("id2", "Návěst 2")
        )
        println("Mockovací dokumenty připraveny: $mockDocuments")
        `when`(mockDataSource.getNavestiList()).thenReturn(mockDocuments)

        // Testování metody v repository
        val result = repository.getNavestiList()
        println("Výsledek volání getNavestiList: $result")

        // Ověření, že výsledek je správný
        assertNotNull(result)
        println("Výsledek není null.")
        assertEquals(2, result.size)
        println("Počet dokumentů odpovídá: ${result.size}")
        assertEquals("Návěst 1", result[0].second)
        println("První dokument odpovídá: ${result[0].second}")
        println("Test testGetNavestiList úspěšně dokončen.")
    }

    @Test
    fun testGetEvent() = runBlocking {
        println("Začátek testu: testGetEvent")

        // Mockování dat vrácených z metody getEvent v dataSource
        val eventId = "id1"
        val mockEvent = Event(
            description = "Popis",
            name = "Návěst 1",
            isAllTab120Visible = true,
            isAllHeadSignalVisible = true,
            isShowLinesVisible = true,
            signalColors = listOf(),
            isAllSpeedLinesVisible = true,
            speedLinesColors = listOf(),
            isAllTabNumberVisible = true,
            allTabNumberText = "5",
            isAllTabPictureVisible = true,
            allTabPictureText = "oddil",
            isAllShiftBoxVisible = true,
            shiftColors = listOf()
        )
        println("Mockovací event připraven: $mockEvent")
        `when`(mockDataSource.getEvent(eventId)).thenReturn(mockEvent)

        // Volání metody repository a ověření výsledku
        val result = repository.getEvent(eventId)
        println("Výsledek volání getEvent: $result")

        // Ověření, že výsledek není null a data odpovídají očekávaným hodnotám
        assertNotNull(result)
        println("Výsledek není null.")
        assertEquals("Návěst 1", result?.name)
        println("Název události odpovídá: ${result?.name}")
        assertEquals("Popis", result?.description)
        println("Popis události odpovídá: ${result?.description}")
        println("Test testGetEvent úspěšně dokončen.")
    }

    @Test
    fun testGetRandomEventDocument() = runBlocking {
        println("Začátek testu: testGetRandomEventDocument")

        // Mockování chování metody getRandomEventDocument v dataSource
        val mockDoc: DocumentSnapshot = mock()
        println("Mockovací dokument připraven.")
        `when`(mockDataSource.getRandomEventDocument()).thenReturn(mockDoc)

        // Volání metody repository
        val result = repository.getRandomEventDocument()
        println("Výsledek volání getRandomEventDocument: $result")

        // Ověření výsledku
        assertNotNull(result)
        println("Výsledek není null.")
        println("Test testGetRandomEventDocument úspěšně dokončen.")
    }
}
