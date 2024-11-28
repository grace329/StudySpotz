package com.example.studyspotz.persistence

import com.example.studyspotz.model.StudySpot

class StudySpotStorageMock : IPersistence {
    private val studySpots = listOf(
        StudySpot(id = "1", building = "Building A", room = "101"),
        StudySpot(id = "2", building = "Building B", room = "202"),
        StudySpot(id = "3", building = "Building C", room = "303"),
        StudySpot(id = "4", building = "MC", room = "404")
    )

    private val userFavorites = mutableMapOf<String, MutableList<String>>()

    override fun getCurrentUserId(): String? {
        return "mockUserId"
    }

    override suspend fun getAllStudySpots(): List<StudySpot> {
        return studySpots
    }

    override suspend fun getFavoriteStudySpots(userId: String): List<String> {
        return userFavorites[userId] ?: emptyList()
    }

    override suspend fun addFavorite(userId: String, spotId: String) {
        val favorites = userFavorites.getOrPut(userId) { mutableListOf() }
        if (!favorites.contains(spotId)) favorites.add(spotId)
    }

    override suspend fun removeFavorite(userId: String, spotId: String) {
        userFavorites[userId]?.remove(spotId)
    }
}

