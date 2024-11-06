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

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null) // Optional error state
    val error: StateFlow<String?> = _error

    init {
        fetchAllStudySpots()
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
}