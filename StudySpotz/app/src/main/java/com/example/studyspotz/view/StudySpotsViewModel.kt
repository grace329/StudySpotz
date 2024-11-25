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
        filterStudySpots("","All", false)
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

    fun filterStudySpots(search: String, filter: String, showFavoritesOnly: Boolean): List<StudySpot> {
        return _studySpots.value.filter { spot ->
            val matchesSearch = search.isEmpty() || spot.building.contains(search, ignoreCase = true) ||
                    spot.room.contains(search, ignoreCase = true) ||
                    spot.location.contains(search, ignoreCase = true) ||
                    (spot.faculty?.contains(search, ignoreCase = true) == true)
            val matchesFilter = filter == "All" || (spot.faculty?.contains(filter, ignoreCase = true) == true)
            val matchesFavorites = !showFavoritesOnly || _favoriteSpots.value.contains(spot.id)
            matchesSearch && matchesFilter && matchesFavorites
        }
    }
}