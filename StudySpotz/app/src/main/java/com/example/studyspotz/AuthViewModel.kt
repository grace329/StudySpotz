package com.example.studyspotz

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.rpc.context.AttributeContext.Auth

class AuthViewModel : ViewModel() {
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus() {
        if(auth.currentUser == null) {
            _authState.value = AuthState.Unauthenticated
        } else {
            _authState.value = AuthState.Authenticated
        }

    }

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or password cannot be empty")
        }
        // Set to loading initially
        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
                }
            }
    }

    fun signup(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or password cannot be empty")
        }
        // Set to loading initially
        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                    // Authentication successful, add user to Firestore
                    val user = auth.currentUser
                    user?.let {
                        addUserToFirestore(it.uid, email)
                    }
                } else {
                    _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
                }
            }
    }

    fun signout() {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }

    fun addUserToFirestore(uid: String, email: String) {
        val user = hashMapOf(
            "email" to email,
            "favoriteStudySpots" to arrayListOf<String>()
        )

        db.collection("users")
            .document(uid)
            .set(user)
            .addOnSuccessListener {
                // User added successfully to Firestore
                Log.d("Firestore", "User added to Firestore")
            }
            .addOnFailureListener { e ->
                // Handle failure
                Log.e("Firestore", "Error adding user to Firestore", e)
            }
    }




}


sealed class AuthState {
    object Authenticated : AuthState()
    object Unauthenticated: AuthState()
    object Loading : AuthState()
    data class Error(val message:String) : AuthState()
}