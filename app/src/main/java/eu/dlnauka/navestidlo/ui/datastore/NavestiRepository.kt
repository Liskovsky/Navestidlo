package eu.dlnauka.navestidlo.ui.datastore

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.perf.FirebasePerformance

// Repository vrstva, která zajišťuje přístup k datům.
class NavestiRepository(

    // Výchozí implementace s Firestore
    private val dataSource: NavestiDataSource = FirestoreDataSource()
) {
    // Získá seznam návěstí (id + název)
    suspend fun getNavestiList(): List<Pair<String, String>> {
        return try {

            // Použití mezivrstvy pro získání dat
            dataSource.getNavestiList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Získá konkrétní událost podle jejího ID
    suspend fun getEvent(eventId: String): Event? {
        // val trace = FirebasePerformance.getInstance().newTrace("get_event_trace")
        return try {
            // trace.start()

            // Použití mezivrstvy pro získání dat
            val event = dataSource.getEvent(eventId)
            // trace.stop()
            event
        } catch (e: Exception) {
            // trace.stop()
            null
        }
    }

    // Získá náhodný dokument z kolekce "Návěsti"
    suspend fun getRandomEventDocument(): DocumentSnapshot? {
        return dataSource.getRandomEventDocument()
    }

    // Vytvoří náhodnou otázku pro kvíz
    suspend fun getRandomQuestion(): Pair<Event, List<String>>? {
        return try {
            // Načte všechny dokumenty z kolekce "Návěsti"
            val documents = dataSource.getNavestiList()

            // Vybere náhodný dokument z načtených dokumentů
            val randomDoc = documents.randomOrNull() ?: return null
            val event = getEvent(randomDoc.first) ?: return null

            // Načte všechny data z názvů dokumentů
            val allNames = documents.map { it.second }

            // Vybere také náhodné nesprávné odpovědi
            val randomWrongAnswers = allNames.shuffled().take(2)

            // Vytvoří seznam správných a špatných odpovědí a zamíchá je
            val answerOptions = (randomWrongAnswers + event.name).shuffled()

            // Aktualizuje událost s správnou odpovědí
            val updatedEvent = event.copy(correctAnswer = event.name)

            Pair(updatedEvent, answerOptions)
        } catch (e: Exception) {
            null
        }
    }
}
