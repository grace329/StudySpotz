package com.example.studyspotz.persistence

import android.util.Log
import com.example.studyspotz.model.StudySpot
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseStorage(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) : IPersistence {


    override fun getCurrentUserId(): String? {
        Log.d("StudySpotsModel", "userId is ${auth.currentUser?.uid}")
        return auth.currentUser?.uid
    }

    override suspend fun getAllStudySpots(): List<StudySpot> {
        val studySpots = mutableListOf<StudySpot>()
        Log.d("StudySpotsModel", "the userId is ${getCurrentUserId()}")
        try {
            // Get the 'Faculty' document from the 'buildings' collection
            val facultyDoc = firestore.collection("buildings").document("Faculty").get().await()

            // Check if the 'Faculty' document exists
            if (facultyDoc.exists()) {
                // List all faculties, e.g., ["Arts", "Engineering", "Math"]
                val faculties = listOf("Arts", "Engineering", "Math", "Health", "Environment", "Science")

                for (faculty in faculties) {
                    val facultyCollection = facultyDoc.reference.collection(faculty).get().await()

                    if (facultyCollection.isEmpty) {
                        Log.d("StudySpotsModel", "No documents found in faculty: $faculty")
                    } else {
                        facultyCollection.documents.forEach { document ->
                            val spot = document.toObject(StudySpot::class.java)?.apply {
                                id = document.id
                                building = document.getString("Building") ?: ""
                                room = document.getString("Room") ?: ""
                                images = document.get("Images") as? List<String> ?: emptyList()
                                location = document.getString("Location") ?: ""
                                this.faculty = faculty
                            }
                            if (spot != null) {
                                studySpots.add(spot)
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            // Handle error if needed, or log
            Log.e("StudySpotsModel", "Error fetching study spots", e)
        }
        Log.d("StudySpotsModel", "Fetched Study Spots: $studySpots")
        return studySpots
    }

    override suspend fun getFavoriteStudySpots(userId: String): List<String> {
        // Implementation remains the same as before
        Log.d("StudySpotsModel", "Fetching favorite spots for user: $userId")
        return userId?.let {
            val doc = firestore.collection("users").document(it).get().await()
            val favoriteSpots = doc.get("favoriteStudySpots") as? List<String> ?: emptyList()
            Log.d("StudySpotsModel", "Fetched favorite study spots: $favoriteSpots")
            favoriteSpots
        } ?: emptyList()
    }

    override suspend fun addFavorite(userId: String, spotId: String) {
        Log.d("StudySpotsModel", "Adding favorite spot $spotId for user: $userId")
        userId?.let {
            val userRef = firestore.collection("users").document(it)
            userRef.update("favoriteStudySpots", FieldValue.arrayUnion(spotId)).await()
            Log.d("StudySpotsModel", "Added $spotId to favorites")
        }
    }

    override suspend fun removeFavorite(userId: String, spotId: String) {
        Log.d("StudySpotsModel", "Removing favorite spot $spotId for user: $userId")
        userId?.let {
            val userRef = firestore.collection("users").document(it)
            userRef.update("favoriteStudySpots", FieldValue.arrayRemove(spotId)).await()
            Log.d("StudySpotsModel", "Removed $spotId from favorites")
        }
    }
}
