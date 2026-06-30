package com.example.livecity.screens.feed

import com.example.livecity.model.Evaluation
import com.example.livecity.model.Type
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

@OptIn(ExperimentalCoroutinesApi::class)
class MapScreenViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var storageService: StorageService
    private lateinit var viewModel: MapScreenViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        storageService = mockk()
        coEvery { storageService.getAlerts() } returns emptyList()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initialState_isCorrect() = runBlocking {
        viewModel = MapScreenViewModel(storageService)
        val state = viewModel.uiState.first()

        assertEquals(false, state.isMapLoaded)
        assertEquals(3, state.navItems.size)
        assertEquals(emptyList(), state.listOfEvaluations)
        assertEquals(false, state.showBottomSheet)
        assertEquals(null, state.actualItem)
    }

    @Test
    fun onNavItemClicked_updatesSelectedItem() = runBlocking {
        viewModel = MapScreenViewModel(storageService)
        val navItem = viewModel.uiState.first().navItems[1] // AddCircle item

        viewModel.onNavItemClicked(navItem)

        val state = viewModel.uiState.first()
        assertEquals(navItem, state.selectedNavItem)
    }

    @Test
    fun changeBottomSheetState_togglesShowBottomSheet() = runBlocking {
        viewModel = MapScreenViewModel(storageService)
        var state = viewModel.uiState.first()
        assertEquals(false, state.showBottomSheet)

        viewModel.changeBottomSheetState()
        state = viewModel.uiState.first()
        assertEquals(true, state.showBottomSheet)

        viewModel.changeBottomSheetState()
        state = viewModel.uiState.first()
        assertEquals(false, state.showBottomSheet)
    }

    @Test
    fun getAllAlerts_retrievesAlertsSuccessfully() = runBlocking {
        val testAlerts = listOf(
            Evaluation(
                id = "1",
                title = "Test Alert 1",
                description = "Description 1",
                position = GeoPoint(-12.9714, -38.5014),
                type = Type("Danger", "ic_danger"),
                formattedAddress = "Rua Test, 123"
            ),
            Evaluation(
                id = "2",
                title = "Test Alert 2",
                description = "Description 2",
                position = GeoPoint(-12.9715, -38.5015),
                type = Type("Building", "ic_building"),
                formattedAddress = "Rua Test 2, 456"
            )
        )

        coEvery { storageService.getAlerts() } returns testAlerts
        viewModel = MapScreenViewModel(storageService)

        val state = viewModel.uiState.first()
        assertEquals(2, state.listOfEvaluations.size)
        assertEquals("Test Alert 1", state.listOfEvaluations[0].getTitle())
        assertEquals("Test Alert 2", state.listOfEvaluations[1].getTitle())
    }

    @Test
    fun getAllAlerts_withEmptyList() = runBlocking {
        coEvery { storageService.getAlerts() } returns emptyList()
        viewModel = MapScreenViewModel(storageService)

        val state = viewModel.uiState.first()
        assertEquals(0, state.listOfEvaluations.size)
    }

    @Test
    fun onClusterItemClick_updatesActualItem() = runBlocking {
        viewModel = MapScreenViewModel(storageService)
        val clusterItem = MyClusterItem(
            position = LatLng(-12.9714, -38.5014),
            title = "Test Item",
            snippet = "Test Snippet",
            iconResId = "ic_danger",
            formattedAddress = "Test Address",
            date = "01/01/2024"
        )

        viewModel.onClusterItemClick(clusterItem)

        val state = viewModel.uiState.first()
        assertEquals(clusterItem, state.actualItem)
    }

    @Test
    fun myClusterItem_implementsClusterItemInterface() = runBlocking {
        val position = LatLng(-12.9714, -38.5014)
        val clusterItem = MyClusterItem(
            position = position,
            title = "Test",
            snippet = "Snippet",
            iconResId = "ic_danger",
            formattedAddress = "Address",
            date = "01/01/2024"
        )

        assertEquals(position, clusterItem.getPosition())
        assertEquals("Test", clusterItem.getTitle())
        assertEquals("Snippet", clusterItem.getSnippet())
        assertEquals(0F, clusterItem.getZIndex())
    }
}

