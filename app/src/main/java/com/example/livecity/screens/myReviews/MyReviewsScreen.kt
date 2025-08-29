package com.example.livecity.screens.myReviews

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.livecity.R

@Composable
fun MyAlertsScreen(){

}

@Composable
fun AlertsList(){

}

@Composable
fun AlertsCard(){
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxHeight(0.85f)
            .fillMaxWidth(0.75f),
        horizontalAlignment = Alignment.Start,
        ) {
        Row {
            Text(
                text = "Car Accident"
            )
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.car_crash_50dp_ea3323_fill0_wght400_grad0_opsz48),
                contentDescription = "Car crash icon"
            )
        }

    }
}

@Composable
@Preview
fun AlertsCardPreview(){
    AlertsCard()
}