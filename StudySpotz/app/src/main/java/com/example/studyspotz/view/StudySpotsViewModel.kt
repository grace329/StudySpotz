package com.example.studyspotz.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyspotz.model.StudySpot
import com.example.studyspotz.model.StudySpotsModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StudySpotViewModel(private val repository: StudySpotsModel) : ViewModel() {

    private val _studySpots = MutableStateFlow<List<StudySpot>>(emptyList())
    val studySpots: StateFlow<List<StudySpot>> = _studySpots

    private val _favoriteSpots = MutableStateFlow<List<String>>(emptyList())
    val favoriteSpots: StateFlow<List<String>> = _favoriteSpots

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null) // Optional error state
    val error: StateFlow<String?> = _error

    init {
        fetchAllStudySpots()
        fetchFavoriteSpots()
    }

    private fun fetchAllStudySpots() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val spots = repository.getAllStudySpots()
                _studySpots.value = spots
            } catch (e: Exception) {
                _error.value = "Failed to load study spots" // Set error message
                _studySpots.value = emptyList() // Clear study spots on error
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun fetchFavoriteSpots() {
        viewModelScope.launch {
            try {
                val favoriteIds = repository.getFavoriteStudySpots()
                _favoriteSpots.value = favoriteIds
            } catch (e: Exception) {
                _error.value = "Failed to load favorite spots"
            }
        }
    }

    fun isFavorite(spotId: String): StateFlow<Boolean> {
        val isFavorited = _favoriteSpots.value.contains(spotId)
        // Because we are observing the favouriteSpots state
        return MutableStateFlow(isFavorited)
    }

    fun toggleFavorite(spotId: String) {
        viewModelScope.launch {
            try {
                // Collect the current favorite status from the StateFlow
                val isFavorited = _favoriteSpots.value.contains(spotId) // check from the list in ViewModel

                if (isFavorited) {
                    repository.removeFavorite(spotId)
                    // Remove the spot from the favouriteSpots list
                    _favoriteSpots.value -= spotId
                } else {
                    repository.addFavorite(spotId)
                    // Add the spot to the favouriteSpots list
                    _favoriteSpots.value += spotId
                }
            } catch (e: Exception) {
                _error.value = "Failed to update favorite spots"
            }
        }
    }
}