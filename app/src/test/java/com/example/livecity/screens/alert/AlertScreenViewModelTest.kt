package com.example.livecity.screens.alert

import com.example.livecity.service.AccountService
import com.example.livecity.service.module.StorageService
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class AlertScreenViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var storageService: StorageService
    private lateinit var accountService: AccountService
    private lateinit var viewModel: AlertScreenViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        storageService = mockk()
        accountService = mockk()
        coEvery { accountService.currentUserId } returns "testUserId"
        viewModel = AlertScreenViewModel(storageService, accountService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initialState_isCorrect() = runBlocking {
        val state = viewModel.uiState.first()

        assertEquals("", state.title)
        assertEquals("", state.description)
        assertEquals("" to "", state.type)
        assertEquals(null, state.position)
        assertEquals(false, state.expanded)
        assertEquals(false, state.isLoaded)
        assertEquals(false, state.useMyLocation)
        assertEquals(false, state.useSetLocation)
        assertEquals(false, state.expandedGoogleMaps)
        assertEquals("", state.message)
        assertEquals(4, state.listOfAlerts.size)
    }

    @Test
    fun setTitle_updatesTitle() = runBlocking {
        val testTitle = "Car Crash"
        viewModel.setTitle(testTitle)

        val state = viewModel.uiState.first()
        assertEquals(testTitle, state.title)
    }

    @Test
    fun setDescription_updatesDescription() = runBlocking {
        val testDescription = "Collision between two vehicles"
        viewModel.setDescription(testDescription)

        val state = viewModel.uiState.first()
        assertEquals(testDescription, state.description)
    }

    @Test
    fun setType_updatesType() = runBlocking {
        val testType = "Car Crash" to "ic_car_crash"
        viewModel.setType(testType)

        val state = viewModel.uiState.first()
        assertEquals(testType, state.type)
    }

    @Test
    fun setPosition_updatesPosition() = runBlocking {
        val testPosition = -12.9714 to -38.5014
        viewModel.setPosition(testPosition)

        val state = viewModel.uiState.first()
        assertEquals(GeoPoint(testPosition.first, testPosition.second), state.position)
    }

    @Test
    fun updateExpanded_togglesExpanded() = runBlocking {
        var state = viewModel.uiState.first()
        assertFalse(state.expanded)

        viewModel.updateExpanded()
        state = viewModel.uiState.first()
        assertTrue(state.expanded)

        viewModel.updateExpanded()
        state = viewModel.uiState.first()
        assertFalse(state.expanded)
    }

    @Test
    fun onMapLoaded_setsIsLoaded() = runBlocking {
        viewModel.onMapLoaded()

        val state = viewModel.uiState.first()
        assertTrue(state.isLoaded)
    }

    @Test
    fun setCurrentLocation_updatesCurrentLocation() = runBlocking {
        val testLocation = LatLng(-12.9714, -38.5014)
        viewModel.setCurrentLocation(testLocation)

        val state = viewModel.uiState.first()
        assertEquals(testLocation, state.currentLocation)
    }

    @Test
    fun setMarkerPositionSelectedByUser_updatesMarkerPosition() = runBlocking {
        val testLocation = LatLng(-12.9814, -38.5114)
        viewModel.setMarkerPositionSelectedByUser(testLocation)

        val state = viewModel.uiState.first()
        assertEquals(testLocation, state.markerPositionSelectedByUser)
    }

    @Test
    fun setUseMyLocation_updatesUseMyLocation() = runBlocking {
        viewModel.setUseMyLocation(true)

        var state = viewModel.uiState.first()
        assertTrue(state.useMyLocation)

        viewModel.setUseMyLocation(false)
        state = viewModel.uiState.first()
        assertFalse(state.useMyLocation)
    }

    @Test
    fun setUseSetLocation_updatesUseSetLocation() = runBlocking {
        viewModel.setUseSetLocation(true)

        var state = viewModel.uiState.first()
        assertTrue(state.useSetLocation)

        viewModel.setUseSetLocation(false)
        state = viewModel.uiState.first()
        assertFalse(state.useSetLocation)
    }

    @Test
    fun setExpandedGoogleMaps_togglesExpandedGoogleMaps() = runBlocking {
        var state = viewModel.uiState.first()
        assertFalse(state.expandedGoogleMaps)

        viewModel.setExpandedGoogleMaps()
        state = viewModel.uiState.first()
        assertTrue(state.expandedGoogleMaps)
    }

    @Test
    fun saveAlert_withEmptyTitle_showsError() = runBlocking {
        viewModel.setDescription("Description")
        viewModel.setType("Danger" to "ic_danger")
        viewModel.setUseMyLocation(true)

        var onSavedCalled = false
        viewModel.saveAlert { onSavedCalled = true }

        val state = viewModel.uiState.first()
        assertEquals("Title cannot be empty", state.message)
        assertEquals(false, onSavedCalled)
    }

    @Test
    fun saveAlert_withEmptyDescription_showsError() = runBlocking {
        viewModel.setTitle("Car Crash")
        viewModel.setType("Danger" to "ic_danger")
        viewModel.setUseMyLocation(true)

        var onSavedCalled = false
        viewModel.saveAlert { onSavedCalled = true }

        val state = viewModel.uiState.first()
        assertEquals("Description cannot be empty", state.message)
        assertEquals(false, onSavedCalled)
    }

    @Test
    fun saveAlert_withEmptyType_showsError() = runBlocking {
        viewModel.setTitle("Car Crash")
        viewModel.setDescription("Description")
        viewModel.setUseMyLocation(true)

        var onSavedCalled = false
        viewModel.saveAlert { onSavedCalled = true }

        val state = viewModel.uiState.first()
        assertEquals("Type cannot be empty", state.message)
        assertEquals(false, onSavedCalled)
    }

    @Test
    fun saveAlert_withNoLocation_showsError() = runBlocking {
        viewModel.setTitle("Car Crash")
        viewModel.setDescription("Description")
        viewModel.setType("Danger" to "ic_danger")

        var onSavedCalled = false
        viewModel.saveAlert { onSavedCalled = true }

        val state = viewModel.uiState.first()
        assertEquals("Location cannot be empty", state.message)
        assertEquals(false, onSavedCalled)
    }
}

