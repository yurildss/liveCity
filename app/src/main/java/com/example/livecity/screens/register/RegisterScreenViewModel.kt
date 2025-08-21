package com.example.livecity.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livecity.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterScreenViewModel @Inject constructor(
    private val accountService: AccountService
): ViewModel() {

    val _state = MutableStateFlow(RegisterScreenState())
    val state = _state.asStateFlow()

    fun onEmailChange(newEmail: String){
        _state.value = _state.value.copy(email = newEmail)
    }

    fun onNameChange(newNome: String){
        _state.value = _state.value.copy(nome = newNome)
    }

    fun onPasswordChange(newPassword: String){
        _state.value = _state.value.copy(password = newPassword)
    }

    fun onConfirmPasswordChange(newConfirmPassword: String){
        _state.value = _state.value.copy(confirmPassword = newConfirmPassword)
    }

    fun onRegisterClick(
        onSuccessfulRegister: () -> Unit
    ){
        viewModelScope.launch {
            if (state.value.email.isBlank()) {
                return@launch
            }
            if (state.value.nome.isBlank()) {
                return@launch
            }

            if (state.value.password.isBlank()) {
                return@launch
            }
            if (state.value.confirmPassword.isBlank()) {
                return@launch
            }

            if (state.value.password != state.value.confirmPassword) {
                return@launch
            }
            try {
                accountService.register(
                    email = state.value.email,
                    password = state.value.password,
                    name = state.value.nome
                )
                onSuccessfulRegister()
            }catch (e: Exception){

            }
        }
    }
}

data class RegisterScreenState(
    val email: String = "",
    val nome: String = "",
    val password: String = "",
    val confirmPassword: String = ""
)