package com.example.studyspotz.model

import android.util.Log
import com.example.studyspotz.persistence.IPersistence

data class StudySpot(
    var id: String = "",
    var building: String = "",
    var room: String = "",
    var images: List<String> = emptyList(),
    var location: String = "",
    var faculty: String? = ""
)

class StudySpotsModel(private val storage: IPersistence) {

    private val userId: String? get() = storage.getCurrentUserId()

    suspend fun getAllStudySpots(): List<StudySpot> {
        return try {
            storage.getAllStudySpots()
        } catch (e: Exception) {
            Log.e("StudySpotsModel", "Error fetching all study spots", e)
            emptyList()
        }
    }

    suspend fun getFavoriteStudySpots(): List<String> {
        return try {
            userId?.let { storage.getFavoriteStudySpots(it) } ?: emptyList()
        } catch (e: Exception) {
            Log.e("StudySpotsModel", "Error fetching favorite study spots", e)
            emptyList()
        }
    }

    suspend fun addFavorite(spotId: String) {
        try {
            userId?.let { storage.addFavorite(it, spotId) }
        } catch (e: Exception) {
            Log.e("StudySpotsModel", "Error adding favorite study spot", e)
        }
    }

    suspend fun removeFavorite(spotId: String) {
        try {
            userId?.let { storage.removeFavorite(it, spotId) }
        } catch (e: Exception) {
            Log.e("StudySpotsModel", "Error removing favorite study spot", e)
        }
    }
}
