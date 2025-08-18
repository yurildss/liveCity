package com.example.livecity.screens.alert

import androidx.lifecycle.ViewModel
import com.example.livecity.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class AlertScreenViewModel: ViewModel()  {
    private val _uiState = MutableStateFlow(AlertScreenUIState())
    val uiState: StateFlow<AlertScreenUIState> = _uiState.asStateFlow()

    fun setTitle(title: String){
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun setDescription(description: String){
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun setType(type: Pair<String, Int>){
        _uiState.value = _uiState.value.copy(type = type)
    }

    fun setPosition(position: Pair<Double, Double>){
        _uiState.value = _uiState.value.copy(position = position)
    }

}

data class AlertScreenUIState(
    val title: String = "",
    val description: String = "",
    val type: Pair<String, Int> = "" to 0,
    val position: Pair<Double, Double> = 0.0 to 0.0,
    val userId: String = "",
    val listOfAlerts: List<Pair<String, Int>> = listOf(
        Pair<String, Int>("Dangerous area", R.drawable.dangerous),
        Pair<String, Int>("Building", R.drawable.build_50dp_ea3323_fill0_wght400_grad0_opsz48),
        Pair<String, Int>("Car crash", R.drawable.car_crash_50dp_ea3323_fill0_wght400_grad0_opsz48),
        Pair<String, Int>("Rain/Snow/Storm", R.drawable.thunderstorm_50dp_ea3323_fill0_wght400_grad0_opsz48),
    )
)