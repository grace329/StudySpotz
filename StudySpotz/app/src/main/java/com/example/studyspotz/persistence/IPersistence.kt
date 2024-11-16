package com.example.studyspotz.persistence

import com.example.studyspotz.model.StudySpot

interface IPersistence {
    suspend fun getAllStudySpots(): List<StudySpot>
    suspend fun getFavoriteStudySpots(userId: String): List<String>
    suspend fun addFavorite(userId: String, spotId: String)
    suspend fun removeFavorite(userId: String, spotId: String)
    fun getCurrentUserId(): String?
}