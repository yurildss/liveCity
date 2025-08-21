package com.example.livecity.screens.feed

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class MapScreenViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(MapScreenUIState())
    val uiState = _uiState.asStateFlow()

    fun onNavItemClicked(navItem: NavItem){
        _uiState.value = _uiState.value.copy(selectedNavItem = navItem)
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
            description = "AddCircle",
            testTag = "addScreen"
        ),
        NavItem(
            icon = Icons.Default.AccountCircle,
            description = "AccountCircle",
            testTag = "accountScreen"
        )
    ),
    val selectedNavItem: NavItem = navItems.first()
)

data class NavItem(
    val icon: ImageVector,
    val description: String,
    val testTag: String

)