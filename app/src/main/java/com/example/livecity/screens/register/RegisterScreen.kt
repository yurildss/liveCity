package com.example.livecity.screens.register

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegisterScreen(
    onSuccessfulRegister: () -> Unit,
    viewModel: RegisterScreenViewModel = hiltViewModel()
){
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.message) {
        if (uiState.message.isNotBlank()) {
            snackBarHostState.showSnackbar(
                message = uiState.message,
                withDismissAction = true
            )
            viewModel.clearMessage()
        }
    }

    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackBarHostState)
    }) {
        FieldsRegister(
            onRegisterClick = { viewModel.onRegisterClick(onSuccessfulRegister) },
            onEmailChange = viewModel::onEmailChange,
            onPasswordChange = viewModel::onPasswordChange,
            onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
            onNameChange = viewModel::onNameChange,
            uiState = uiState
        )
    }
}

@Composable
fun FieldsRegister(
    onRegisterClick: () -> Unit,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    uiState: RegisterScreenState
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            onValueChange = onNameChange,
            value = uiState.nome,
            label = { Text(text = "Name")},
            modifier = Modifier
                .padding(top = 100.dp)
                .fillMaxWidth(0.65f)
        )
        OutlinedTextField(
            onValueChange = onEmailChange,
            value = uiState.email,
            label = { Text(text = "Email")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth(0.65f)
        )
        OutlinedTextField(
            onValueChange = onPasswordChange,
            value = uiState.password,
            label = { Text(text = "Password")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth(0.65f),
            visualTransformation = PasswordVisualTransformation()
        )
        OutlinedTextField(
            onValueChange = onConfirmPasswordChange,
            value = uiState.confirmPassword,
            label = { Text(text = "Repeat Password")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth(0.65f),
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = onRegisterClick,
            colors = ButtonDefaults.buttonColors(Color.Black),
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .padding(top = 100.dp)
        ) {
            Text(text = "Register")
        }
    }
}
