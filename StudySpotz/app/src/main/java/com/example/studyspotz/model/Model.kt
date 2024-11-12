package com.example.studyspotz.model

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

data class StudySpot(
    var id: String = "",
    var building: String = "",
    var room: String = "",
    var images: List<String> = emptyList(),
    var location: String = "",
    var faculty: String? = ""
)

class StudySpotsModel(private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()) {
    // Call on the FirebaseAuth to get the userid
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    suspend fun getAllStudySpots(): List<StudySpot> {
        val studySpots = mutableListOf<StudySpot>()
        Log.d("StudySpotsModel", "the userId is $userId")
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

    suspend fun getFavoriteStudySpots(): List<String> {
        Log.d("StudySpotsModel", "Fetching favorite spots for user: $userId")
        return userId?.let {
            val doc = firestore.collection("users").document(it).get().await()
            val favoriteSpots = doc.get("favoriteStudySpots") as? List<String> ?: emptyList()
            Log.d("StudySpotsModel", "Fetched favorite study spots: $favoriteSpots")
            favoriteSpots
        } ?: emptyList()
    }

    suspend fun addFavorite(spotId: String) {
        Log.d("StudySpotsModel", "Adding favorite spot $spotId for user: $userId")
        userId?.let {
            val userRef = firestore.collection("users").document(it)
            userRef.update("favoriteStudySpots", FieldValue.arrayUnion(spotId)).await()
            Log.d("StudySpotsModel", "Added $spotId to favorites")
        }
    }

    suspend fun removeFavorite(spotId: String) {
        Log.d("StudySpotsModel", "Removing favorite spot $spotId for user: $userId")
        userId?.let {
            val userRef = firestore.collection("users").document(it)
            userRef.update("favoriteStudySpots", FieldValue.arrayRemove(spotId)).await()
            Log.d("StudySpotsModel", "Removed $spotId from favorites")
        }
    }
}