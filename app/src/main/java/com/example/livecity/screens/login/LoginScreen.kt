package com.example.livecity.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun LoginScreen(
    onSuccessfulLogin: () -> Unit,
    viewModel: LoginScreenViewModel = hiltViewModel()
){
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    Fields(
        onLoginClick = { viewModel.onLoginClick(onSuccessfulLogin) },
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        uiState = uiState,
    )
}

@Composable
fun Fields(
    onLoginClick: (onSuccessfulLogin: () -> Unit) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    uiState: LoginScreenState
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        OutlinedTextField(
            onValueChange = onEmailChange,
            value = uiState.email,
            label = { Text(text = "Email")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .padding(top = 100.dp)
                .fillMaxWidth(0.65f)
        )
        OutlinedTextField(
            onValueChange = onPasswordChange,
            value = uiState.password,
            label = { Text(text = "Password")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth(0.65f)
        )
        Button(
            onClick = { onLoginClick } ,
            colors = ButtonDefaults.buttonColors(Color.Black),
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .padding(top = 100.dp)
        ) {
            Text(text = "Login")
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview(){
    Fields(
        onLoginClick = {},
        onEmailChange = {},
        onPasswordChange = {},
        uiState = LoginScreenState()
    )
}