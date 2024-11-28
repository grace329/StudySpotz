package com.example.studyspotz.ViewModel

import com.example.studyspotz.model.StudySpot
import com.example.studyspotz.model.StudySpotsModel
import com.example.studyspotz.persistence.StudySpotStorageMock
import com.example.studyspotz.view.StudySpotViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ViewModelTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testSearchByBuilding() = runTest {
        // Set up the mock data and model
        val storageMock = StudySpotStorageMock()
        val model = StudySpotsModel(storageMock)
        val viewModel = StudySpotViewModel(model)

        // Wait for all coroutines to complete

        advanceUntilIdle()
        val studySpots = viewModel.filterStudySpots("MC", "All", false)

        // Assertions to check if the data returned is as expected
        assertEquals(1, studySpots.size) // Ensure only 1 spot matches "MC"
        assertEquals("MC", studySpots[0].building)

    }
}