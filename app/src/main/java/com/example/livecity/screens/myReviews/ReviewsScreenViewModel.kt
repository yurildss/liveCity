package com.example.livecity.screens.myReviews

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livecity.model.Evaluation
import com.example.livecity.network.GeoCodingApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewsScreenViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow(MyReviewsUiState())
    val uiState: StateFlow<MyReviewsUiState> = _uiState.asStateFlow()

    fun getAddresses(){
       viewModelScope.launch {
           val response = GeoCodingApi.geocodingService.getAddressByGeo("-12.2456,-38.9647")
           Log.d("Geocoding", response.toString())
       }
    }

}

data class MyReviewsUiState(
    val myReviews: List<Evaluation> = emptyList()
)