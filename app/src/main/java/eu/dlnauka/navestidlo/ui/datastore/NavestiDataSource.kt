package eu.dlnauka.navestidlo.ui.datastore

import com.google.firebase.firestore.DocumentSnapshot

// Rozhraní pro získání dat o návěstích
interface NavestiDataSource {
    suspend fun getRandomEventDocument(): DocumentSnapshot?
    suspend fun getEvent(eventId: String): Event?
    suspend fun getNavestiList(): List<Pair<String, String>>
}
