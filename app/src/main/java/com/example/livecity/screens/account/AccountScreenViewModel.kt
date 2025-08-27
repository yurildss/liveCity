package com.example.livecity.screens.account

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livecity.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountScreenViewModel @Inject constructor(
    private val accountService: AccountService
): ViewModel() {

    private val _userName = MutableStateFlow("")
    val userName = _userName.asStateFlow()

    init{
         _userName.value = accountService.currentUserName
    }

    fun logOut(onLogOut: () -> Unit){
        viewModelScope.launch {
            accountService.logOut()
            onLogOut()
        }
    }
}
