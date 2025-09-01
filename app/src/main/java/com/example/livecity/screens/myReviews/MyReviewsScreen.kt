package com.example.livecity.screens.myReviews

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.livecity.R
import com.example.livecity.model.Evaluation

@Composable
fun MyAlertsScreen(
    viewModel: ReviewsScreenViewModel = hiltViewModel()
){
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    AlertsList(myAlerts = uiState.value.myReviews)
}

@Composable
fun AlertsList(myAlerts: List<Evaluation?>){
    if(myAlerts.isEmpty()){
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "No alerts yet",
                        color = Color.Black,
                        fontSize = 20.sp,
                    )
                }
    }else{
        LazyColumn {
                items(myAlerts.size){ index ->
                    AlertsCard(evaluation = myAlerts[index])
                }
        }
    }
}

@Composable
fun AlertsCard(
    evaluation: Evaluation?
){
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxHeight(0.25f)
            .fillMaxWidth(0.95f)
            .border(3.dp, Color.Black, shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp))
        ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = evaluation?.title ?: "",
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
            Text(text = evaluation?.description ?: "",
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
            Text(text = evaluation?.formattedAddress ?: "",
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
            Text(text = evaluation?.date.toString(),
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
            AlertsCard(
                evaluation = Evaluation()
            )
        }
    }
}