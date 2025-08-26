package com.example.livecity.screens.feed

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livecity.model.Evaluation
import com.example.livecity.service.module.StorageService
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapScreenViewModel @Inject constructor(
    private val storageService: StorageService
) : ViewModel() {
    private val _uiState = MutableStateFlow(MapScreenUIState())
    val uiState = _uiState.asStateFlow()

    init {
        getAllAlerts()
    }

    fun onNavItemClicked(navItem: NavItem){
        _uiState.value = _uiState.value.copy(selectedNavItem = navItem)
    }

    fun onMapLoaded(){
        _uiState.value = _uiState.value.copy(isMapLoaded = true)
    }

    fun getAllAlerts(){
        viewModelScope.launch {
            val alerts = storageService.getAlerts()
            val alertList = mutableListOf<ClusterItem>()
            alerts.forEach {
                alertList.add(it.toClusterItem())
            }
            _uiState.value = _uiState.value.copy(listOfEvaluations = alertList)
        }
    }

    fun Evaluation.toClusterItem(): MyClusterItem {
        return MyClusterItem(
            position = LatLng(position.first, position.second),
            title = title,
            snippet = description
        )
    }

}

data class MapScreenUIState(
    val navItems: List<NavItem> = listOf(
        NavItem(
            icon = Icons.Default.Home,
            description = "Home",
            testTag = "homeScreen"
        ),
        NavItem(
            icon = Icons.Default.Search,
            description = "Search",
            testTag = "searchScreen"
        ),
        NavItem(
            icon = Icons.Default.AddCircle,
            description = "Add Alert",
            testTag = "addScreen"
        ),
        NavItem(
            icon = Icons.Default.AccountCircle,
            description = "Account",
            testTag = "accountScreen"
        )
    ),
    val selectedNavItem: NavItem = navItems.first(),
    val isMapLoaded: Boolean = false,
    val listOfAlerts: MutableList<Pair<Double, Double>> = mutableListOf(),
    val listOfEvaluations: List<ClusterItem> = emptyList()
)

data class NavItem(
    val icon: ImageVector,
    val description: String,
    val testTag: String
)

data class MyClusterItem(
    private val position: LatLng,
    private val title: String,
    private val snippet: String
) : ClusterItem {
    override fun getPosition(): LatLng = position
    override fun getTitle(): String = title
    override fun getSnippet(): String = snippet
    override fun getZIndex(): Float = 0F
}