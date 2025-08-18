package com.example.livecity.screens.alert

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.livecity.R
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun AlertScreen(){

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertForm(){
    val listOfAlerts = listOf(
        Pair<String, Int>("Danger", R.drawable.dangerous)
    )
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
                listOfAlerts.forEach {
                    DropdownMenuItem(
                        text = { Text(text = it.first) },
                        onClick = {}
                    )
                }
            }
        }

    }
}

@Composable
fun UserLocationMap(){
    var isMapLoaded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }
    var currentLocation by remember { mutableStateOf<LatLng?>(null) }
    val cameraPositionState = rememberCameraPositionState()

    var markerPosition by remember { mutableStateOf<LatLng?>(null) }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    val latLng = LatLng(it.latitude, it.longitude)
                    currentLocation = latLng

                    cameraPositionState.position = CameraPosition.fromLatLngZoom(latLng, 15f)
                }
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        GoogleMap(
            onMapLoaded = { isMapLoaded = true },
            cameraPositionState = cameraPositionState,
            properties = com.google.maps.android.compose.MapProperties(
                isMyLocationEnabled = true
            ),
            onMapLongClick = {
                markerPosition = it
            }
        ){
            markerPosition?.let {
                Marker(state = com.google.maps.android.compose.MarkerState(position = it))
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(onClick = {}) {
                Text("Use my actual location")
            }
            markerPosition?.let {
                Button(onClick = {}) {
                    Text("Set this location")
                }
            }
        }
    }
}