package com.example.livecity.screens.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.livecity.model.User

@Composable
fun AccountScreen(
    onLogOut: () -> Unit,
    onMyAlertsClick: () -> Unit,
    viewModel: AccountScreenViewModel = hiltViewModel()
){
    val userName = viewModel.userName.collectAsStateWithLifecycle()

    AccountForm(
        userName = userName.value,
        onMyInfosClick = {  },
        onChangePasswordClick = {  },
        onMyAlertsClick = onMyAlertsClick,
        onLogoutClick = {
            viewModel.logOut(onLogOut)
        }
    )
}

@Composable
fun AccountForm(
    userName: String,
    onMyInfosClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onMyAlertsClick: () -> Unit,
    onLogoutClick: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Text("Hello, $userName")
        Button(
            onClick = {  },
            colors = ButtonDefaults.buttonColors(Color.Black),
            modifier = Modifier.padding(top = 10.dp).fillMaxWidth(0.65f)
        ) {
            Text(text = "My infos")
        }
        Button(
            onClick = {  },
            colors = ButtonDefaults.buttonColors(Color.Black),
            modifier = Modifier.padding(top = 15.dp).fillMaxWidth(0.65f)
        ) {
            Text(text = "Change password")
        }
        Button(
            onClick = {  },
            colors = ButtonDefaults.buttonColors(Color.Black),
            modifier = Modifier.padding(top = 15.dp).fillMaxWidth(0.65f)
        ) {
            Text(text = "My alerts")
        }
        Button(
            onClick = onLogoutClick,
            colors = ButtonDefaults.buttonColors(Color.Black),
            modifier = Modifier.padding(top = 15.dp).fillMaxWidth(0.65f)
        ) {
            Text(text = "Logout")
        }
    }
}