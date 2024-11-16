package com.example.studyspotz.model

import com.example.studyspotz.persistence.StudySpotStorageMock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class StudySpotsModelTest {

    @Test
    fun testGetAllStudySpots() = runTest {
        // Set up the mock data and model
        val storageMock = StudySpotStorageMock()
        val model = StudySpotsModel(storageMock)

        // Call the method to get all study spots
        val studySpots = model.getAllStudySpots()

        // Assertions to check if the data returned is as expected
        assertEquals(3, studySpots.size)
        assertEquals("Building A", studySpots[0].building)
        assertEquals("101", studySpots[0].room)
    }

    @Test
    fun testGetFavoriteStudySpots() = runTest {
        val storageMock = StudySpotStorageMock()
        val model = StudySpotsModel(storageMock)

        // Add some favorite spots to the mock data
        storageMock.addFavorite("mockUserId", "1")
        storageMock.addFavorite("mockUserId", "2")

        // Get the favorites and verify the result
        val favorites = model.getFavoriteStudySpots()
        assertEquals(2, favorites.size)
        assertEquals("1", favorites[0])
        assertEquals("2", favorites[1])
    }

    @Test
    fun testAddFavoriteStudySpot() = runTest {
        val storageMock = StudySpotStorageMock()
        val model = StudySpotsModel(storageMock)

        // Add a favorite spot
        model.addFavorite("1")

        // Verify that the favorite list contains the newly added spot
        val favorites = model.getFavoriteStudySpots()
        assertEquals(1, favorites.size)
        assertEquals("1", favorites[0])
    }

    @Test
    fun testRemoveFavoriteStudySpot() = runTest {
        val storageMock = StudySpotStorageMock()
        val model = StudySpotsModel(storageMock)

        // Add some favorites
        model.addFavorite("1")
        model.addFavorite("2")

        // Remove one
        model.removeFavorite("1")

        // Verify the favorite list has been updated
        val favorites = model.getFavoriteStudySpots()
        assertEquals(1, favorites.size)
        assertEquals("2", favorites[0])
    }
}
