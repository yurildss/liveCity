package com.example.livecity.screens.openscreen

import android.media.Image
import android.provider.CalendarContract.Colors
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.livecity.R
import com.example.livecity.ui.theme.LiveCityTheme

@Composable
fun Greeting(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to Live City",
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            modifier = Modifier.padding(top = 150.dp)
        )
        Image(painter = painterResource(id = R.drawable.chatgpt_image_8_de_set__de_2025__19_34_35), contentDescription = "Logo")
        Button(
            onClick = onLoginClick,
            colors = ButtonDefaults.buttonColors(Color.Black),
            modifier = Modifier.fillMaxWidth(0.65f).padding(top = 50.dp)
        ) {
            Text(text = "Login")
        }
        OutlinedButton(
            onClick = onRegisterClick,
            modifier = Modifier.fillMaxWidth(0.65f)
        ) {
            Text(text = "Register")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LiveCityTheme {
        Greeting(
            onLoginClick = {},
            onRegisterClick = {}
        )
    }
}