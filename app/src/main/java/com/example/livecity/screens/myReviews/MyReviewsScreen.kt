package com.example.livecity.screens.myReviews

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            .fillMaxHeight(0.25f)
            .fillMaxWidth(0.95f)
            .border(3.dp, Color.Black, shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp))
        ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Car Accident",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                color = Color.Black,
                modifier = Modifier.padding(10.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.car_crash_50dp_ea3323_fill0_wght400_grad0_opsz48),
                contentDescription = "Car crash icon",
                modifier = Modifier.padding(10.dp)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.text_ad_40dp_000000_fill0_wght400_grad0_opsz40),
                contentDescription = "Description icon",
                modifier = Modifier.padding(10.dp)
            )
            Text(text = "Description of the alert",
                fontSize = 18.sp,
                color = Color.Black,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(10.dp)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.location_on_40dp_000000_fill0_wght400_grad0_opsz40),
                contentDescription = "Location icon",
                modifier = Modifier.padding(10.dp)
            )
            Text(text = "Location of the alert",
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(10.dp)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.date_range_40dp_000000_fill0_wght400_grad0_opsz40),
                contentDescription = "Date",
                modifier = Modifier.padding(10.dp)
            )
            Text(text = "Date of the alert",
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@Composable
@Preview
fun AlertsCardPreview(){
    Surface {
        Column(modifier = Modifier.fillMaxSize()) {
            AlertsCard()
        }
    }
}