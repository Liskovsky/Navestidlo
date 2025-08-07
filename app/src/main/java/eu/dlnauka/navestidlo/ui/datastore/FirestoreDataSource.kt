package eu.dlnauka.navestidlo.ui.datastore

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import eu.dlnauka.navestidlo.ui.classes.ColorParser
import eu.dlnauka.navestidlo.ui.utils.localized
import kotlinx.coroutines.tasks.await

// Implementace třídy, která komunikuje s Firestore
class FirestoreDataSource(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : NavestiDataSource {

    // Získání seznamu návěstí (id + název)
    override suspend fun getNavestiList(): List<Pair<String, String>> {
        return try {
            val documents = firestore.collection("Navestidla").get().await()
            documents.map {
                val nameMap = it.get("Name") as? Map<String, String> ?: emptyMap()
                val name = nameMap.localized()
                Pair(it.id, name)
            }
        } catch (e: Exception) {
            Log.e("FirestoreDataSource", "Chyba při čtení dat", e)
            emptyList()
        }
    }

    // Získání náhodného dokumentu z kolekce "Návěsti"
    override suspend fun getRandomEventDocument(): DocumentSnapshot? {
        return try {
            val documents = firestore.collection("Navestidla").get().await()
            documents.documents.randomOrNull()
        } catch (e: Exception) {
            Log.e("FirestoreDataSource", "Chyba při čtení dat", e)
            null
        }
    }

    // Získání konkrétní události podle její ID
    override suspend fun getEvent(eventId: String): Event? {
        return try {
            val document = firestore.collection("Navestidla").document(eventId).get().await()
            if (document.exists()) {
                val data = document.data ?: return null
                Event(
                    name = data["Name"] as? Map<String, String> ?: emptyMap(),
                    description = data["Description"] as? Map<String, String> ?: emptyMap(),
                    isAllTab120Visible = data["tab120Visible"] as? Boolean ?: false,
                    isAllHeadSignalVisible = data["hlavniNavestidloVisible"] as? Boolean ?: false,
                    isShowLinesVisible = data["showLines"] as? Boolean ?: false,
                    signalColors = (data["signalColors"] as? List<*>)?.map { ColorParser.parseBlinkColor(it.toString()) } ?: emptyList(),
                    isAllSpeedLinesVisible = data["rychlPruhyVisible"] as? Boolean ?: false,
                    speedLinesColors = (data["pruhyColors"] as? List<*>)?.map { ColorParser.parseBlinkColor(it.toString()) } ?: emptyList(),
                    isAllTabNumberVisible = data["tabCislaVisible"] as? Boolean ?: false,
                    allTabNumberText = data["tabCislaText"] as? String ?: "",
                    isAllTabPictureVisible = data["tabObrazceVisible"] as? Boolean ?: false,
                    allTabPictureText = data["tabObrazceText"] as? String ?: "",
                    isAllShiftBoxVisible = data["posunBoxVisible"] as? Boolean ?: false,
                    shiftColors = (data["posunColors"] as? List<*>)?.map { ColorParser.parseBlinkColor(it.toString()) } ?: emptyList()
                )
            } else null
        } catch (e: Exception) {
            Log.e("FirestoreDataSource", "Chyba při čtení dat", e)
            null
        }
    }
}
