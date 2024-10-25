package com.example.studyspotz

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.example.studyspotz.ui.theme.MobileTheme
import com.example.studyspotz.composables.ListScreen
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class MainActivity : ComponentActivity() {

    val db = Firebase.firestore
    val storage = Firebase.storage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileTheme {
                Surface {
                    // Initialize Voyager Navigator with ListScreen
                    Navigator(screen = ListScreen()) { navigator ->
                        SlideTransition(navigator)
                    }
                }
            }
        }

        val spot = hashMapOf(
            "faculty" to "EV1",
            "room" to "250",
            "floor" to "1"
        )

// Add a new document with a generated ID
        db.collection("buildings")
            .add(spot)
            .addOnSuccessListener { documentReference ->
                Log.d("fb", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("fb", "Error adding document", e)
            }
    }
}
