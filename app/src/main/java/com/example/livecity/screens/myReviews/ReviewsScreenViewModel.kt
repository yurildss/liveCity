package com.example.livecity.screens.myReviews

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livecity.model.Evaluation
import com.example.livecity.network.GeoCodingApi
import com.example.livecity.service.AccountService
import com.example.livecity.service.module.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewsScreenViewModel @Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService
): ViewModel() {

    private val _uiState = MutableStateFlow(MyReviewsUiState())
    val uiState: StateFlow<MyReviewsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val reviews = storageService.getAlertsByUser(accountService.currentUserId)
            _uiState.value = _uiState.value.copy(myReviews = reviews)
        }
    }
}
data class MyReviewsUiState(
    val myReviews: List<Evaluation?> = emptyList()
)