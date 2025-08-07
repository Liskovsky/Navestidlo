package eu.dlnauka.navestidlo.ui.datastore

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import eu.dlnauka.navestidlo.ui.utils.localized
// import com.google.firebase.perf.FirebasePerformance

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
            Log.e("NvestiRepository", "getNavestiList() failed", e)
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
            Log.e("NvestiRepository", "getEvent() failed", e)
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

            // Získá lokalizovaný název události
            val correctName = event.name.localized()

            // Vybere náhodné nesprávné odpovědi kromě správné
            val randomWrongAnswers = documents
                .map { it.second }
                .filterNot { it == correctName }
                .shuffled()
                .take(2)

            // Vytvoří seznam odpovědí a zamíchá
            val answerOptions = (randomWrongAnswers + correctName).shuffled()

            // Aktualizuje event se správnou odpovědí
            val updatedEvent = event.copy(correctAnswer = correctName)

            Pair(updatedEvent, answerOptions)
        } catch (e: Exception) {
            Log.e("NvestiRepository", "getRandomQuestion() failed", e)
            null
        }
    }
}
