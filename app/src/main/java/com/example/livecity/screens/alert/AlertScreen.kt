package com.example.livecity.screens.alert

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AlertScreen(
    onSaved: () -> Unit,
    viewModel: AlertScreenViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if(!uiState.expandedGoogleMaps){
        AlertForm(
            uiState = uiState,
            onTitleChange = viewModel::setTitle,
            onDescriptionChange = viewModel::setDescription,
            listOfAlerts = uiState.listOfAlerts,
            type = viewModel::setType,
            typeSelected = uiState.type.first,
            updateExpanded = viewModel::updateExpanded,
            addLocation = viewModel::setExpandedGoogleMaps,
            onSaveClick = { viewModel.saveAlert(onSaved) },
            onSaved = onSaved
        )
    }
    if (uiState.expandedGoogleMaps) {
        UserLocationMap(
            onMapLoad = viewModel::onMapLoaded,
            setCurrLocation = viewModel::setCurrentLocation,
            markerPosition = uiState.markerPositionSelectedByUser,
            setMarkerPosition = viewModel::setMarkerPositionSelectedByUser,
            setUseMyLocation = viewModel::setUseMyLocation,
            setUseSetLocation = viewModel::setUseSetLocation,
            updateExpanded = viewModel::setExpandedGoogleMaps
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertForm(
    uiState: AlertScreenUIState,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    listOfAlerts: List<Pair<String, String>>,
    type: (Pair<String, String>) -> Unit,
    typeSelected: String,
    updateExpanded: () -> Unit,
    addLocation: () -> Unit,
    onSaveClick: () -> Unit,
    onSaved: () -> Unit

){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            onValueChange = onTitleChange,
            value = uiState.title,
            label = { Text(text = "Title")},
            modifier = Modifier
                .padding(top = 100.dp)
                .fillMaxWidth(0.65f)
        )
        OutlinedTextField(
            onValueChange = onDescriptionChange,
            value = uiState.description,
            label = { Text(text = "Description")},
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth(0.65f)
        )
        ExposedDropdownMenuBox(
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth(0.65f),
            expanded = uiState.expanded,
            onExpandedChange = {updateExpanded()}
        ) {
            OutlinedTextField(
                value = typeSelected,
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
                expanded = uiState.expanded,
                onDismissRequest = {}
            ) {
                listOfAlerts.forEach {
                    DropdownMenuItem(
                        text = { Text(text = it.first) },
                        onClick = {
                            type(Pair(it.first, it.second))
                            updateExpanded()
                        }
                    )
                }
            }
        }
        Button(
            onClick = addLocation,
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth(0.65f),
            colors = ButtonDefaults.buttonColors(Color.Black)
        ){
            Text(text = "Add location")
        }
        Button(onClick = {
                onSaveClick()
            }, modifier = Modifier
            .padding(top = 15.dp)
            .fillMaxWidth(0.65f), colors = ButtonDefaults.buttonColors(Color.Black)){
            Text(text = "Save")
        }
    }
}

@Composable
fun UserLocationMap(
    onMapLoad: () -> Unit,
    setCurrLocation: (LatLng) -> Unit,
    markerPosition: LatLng?,
    setMarkerPosition: (LatLng) -> Unit,
    setUseMyLocation: (Boolean) -> Unit,
    setUseSetLocation: (Boolean) -> Unit,
    updateExpanded: () -> Unit
){

    val context = LocalContext.current
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    val latLng = LatLng(it.latitude, it.longitude)
                    setCurrLocation(latLng)

                    cameraPositionState.position = CameraPosition.fromLatLngZoom(latLng, 15f)
                }
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        GoogleMap(
            onMapLoaded = { onMapLoad()},
            cameraPositionState = cameraPositionState,
            properties = com.google.maps.android.compose.MapProperties(
                isMyLocationEnabled = true
            ),
            onMapLongClick = {
                setMarkerPosition(it)
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
            Button(onClick = {
                setUseMyLocation(true)
                updateExpanded()
            }) {
                Text("Use my actual location")
            }
            markerPosition?.let {
                Button(onClick = {
                    setUseSetLocation(true)
                    updateExpanded()
                }) {
                    Text("Set this location")
                }
            }
        }
    }
}