package com.example.livecity.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livecity.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel@Inject constructor(
    private val accountService: AccountService
) : ViewModel() {
    private val _state = MutableStateFlow(LoginScreenState())
    val state = _state.asStateFlow()

    fun onEmailChange(newEmail: String){
        _state.value = _state.value.copy(email = newEmail)
    }

    fun onPasswordChange(newPassword: String){
        _state.value = _state.value.copy(password = newPassword)
    }

    fun onLoginClick(onSuccessfulLogin: () -> Unit){
        viewModelScope.launch {
            if(state.value.email.isBlank()){
                return@launch
            }
            if(state.value.password.isBlank()){
                return@launch
            }

            accountService.authenticate(
                email = state.value.email,
                password = state.value.password
            )
            onSuccessfulLogin()
        }
    }
}

data class LoginScreenState(
    val email: String = "",
    val password: String = ""
)