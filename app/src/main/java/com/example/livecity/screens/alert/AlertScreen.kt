package com.example.livecity.screens.alert

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.livecity.R

@Composable
fun AlertScreen(){

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertForm(){
    val listOfAlerts = listOf(Pair<String, Int>("Danger", R.drawable.dangerous_50dp_ea3323_fill0_wght400_grad0_opsz48))
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            onValueChange = {},
            value = "",
            label = { Text(text = "Title")},
            modifier = Modifier.padding(top = 100.dp).fillMaxWidth(0.65f)
        )
        OutlinedTextField(
            onValueChange = {},
            value = "",
            label = { Text(text = "Description")},
            modifier = Modifier.padding(top = 15.dp).fillMaxWidth(0.65f)
        )
        ExposedDropdownMenuBox(
            modifier = Modifier.padding(top = 15.dp).fillMaxWidth(0.65f),
            expanded = false,
            onExpandedChange = {}
        ) {
            OutlinedTextField(
                value = "",
                readOnly = true,
                onValueChange = {},
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                label = { Text(text = "Category") },
                trailingIcon = {
                    Icon(
                        Icons
                            .AutoMirrored
                            .Filled
                            .KeyboardArrowRight,
                        contentDescription = ""
                    )
                }
            )
            ExposedDropdownMenu(
                expanded = false,
                onDismissRequest = {}
            ) {

            }
        }
    }
}