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
        advanceUntilIdle()

        // Assertions to check if the data returned is as expected
        assertEquals(1, studySpots.size) // Ensure only 1 spot matches "MC"
        assertEquals("MC", studySpots[0].building)
    }

    @Test
    fun testSearchByRoom() = runTest {
        // Set up the mock data and model
        val storageMock = StudySpotStorageMock()
        val model = StudySpotsModel(storageMock)
        val viewModel = StudySpotViewModel(model)

        // Wait for all coroutines to complete
        advanceUntilIdle()
        val studySpots = viewModel.filterStudySpots("10", "All", false)
        advanceUntilIdle()

        // Assertions to check if the data returned is as expected
        assertEquals(2, studySpots.size)
        assertEquals("101", studySpots[0].room)
        assertEquals("105", studySpots[1].room)
    }

    @Test
    fun testSearchByFaculty() = runTest {
        // Set up the mock data and model
        val storageMock = StudySpotStorageMock()
        val model = StudySpotsModel(storageMock)
        val viewModel = StudySpotViewModel(model)

        // Wait for all coroutines to complete
        advanceUntilIdle()
        val studySpots = viewModel.filterStudySpots("Math", "All", false)
        advanceUntilIdle()

        // Assertions to check if the data returned is as expected
        assertEquals(1, studySpots.size)
        assertEquals("Math", studySpots[0].faculty)
    }

    @Test
    fun testSearchReturnsEmpty() = runTest {
        // Set up the mock data and model
        val storageMock = StudySpotStorageMock()
        val model = StudySpotsModel(storageMock)
        val viewModel = StudySpotViewModel(model)

        // Wait for all coroutines to complete
        advanceUntilIdle()
        val studySpots = viewModel.filterStudySpots("???", "All", false)
        advanceUntilIdle()

        // Assertions to check if the data returned is as expected
        assertEquals(0, studySpots.size)
    }

    @Test
    fun testFilterByFaculty() = runTest {
        // Set up the mock data and model
        val storageMock = StudySpotStorageMock()
        val model = StudySpotsModel(storageMock)
        val viewModel = StudySpotViewModel(model)

        // Wait for all coroutines to complete

        advanceUntilIdle()
        val studySpots = viewModel.filterStudySpots("", "Math", false)
        advanceUntilIdle()
        // Assertions to check if the data returned is as expected
        assertEquals(1, studySpots.size) // Ensure only 1 spot matches "MC"
        assertEquals("Math", studySpots[0].faculty)
    }

    @Test
    fun testFilterReturnEmpty() = runTest {
        // Set up the mock data and model
        val storageMock = StudySpotStorageMock()
        val model = StudySpotsModel(storageMock)
        val viewModel = StudySpotViewModel(model)

        // Wait for all coroutines to complete
        advanceUntilIdle()
        val studySpots = viewModel.filterStudySpots("", "???", false)
        advanceUntilIdle()
        // Assertions to check if the data returned is as expected
        assertEquals(0, studySpots.size) // Ensure only 1 spot matches "MC"
    }

    @Test
    fun testFilterReturnAll() = runTest {
        // Set up the mock data and model
        val storageMock = StudySpotStorageMock()
        val model = StudySpotsModel(storageMock)
        val viewModel = StudySpotViewModel(model)

        // Wait for all coroutines to complete
        advanceUntilIdle()
        val studySpots = viewModel.filterStudySpots("", "", false)
        advanceUntilIdle()

        // Assertions to check if the data returned is as expected
        // two results for Search "10" (as tested above). But we apply "Math" filter so it should just be one.
        assertEquals(5, studySpots.size)
    }

    @Test
    fun testFilterAndSearch() = runTest {
        // Set up the mock data and model
        val storageMock = StudySpotStorageMock()
        val model = StudySpotsModel(storageMock)
        val viewModel = StudySpotViewModel(model)

        // Wait for all coroutines to complete
        advanceUntilIdle()
        val studySpots = viewModel.filterStudySpots("10", "Math", false)
        advanceUntilIdle()

        // Assertions to check if the data returned is as expected
        // two results for Search "10" (as tested above). But we apply "Math" filter so it should just be one.
        assertEquals(1, studySpots.size)
        assertEquals("Math", studySpots[0].faculty)
        assertEquals("105", studySpots[0].room)
    }


}