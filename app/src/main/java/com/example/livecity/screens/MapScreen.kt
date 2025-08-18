package com.example.livecity.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.clustering.Clustering
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState

@Composable
fun FeedMapScreen(){

}

@OptIn(MapsComposeExperimentalApi::class, ExperimentalPermissionsApi::class)
@Composable
fun Map(){

    val singapore = LatLng(1.35, 103.87)
    val singaporeMarkerState = rememberUpdatedMarkerState(position = singapore)

    var isMapLoaded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }
    var currentLocation by remember { mutableStateOf<LatLng?>(null) }
    val cameraPositionState = rememberCameraPositionState()

    var markerPosition by remember { mutableStateOf<LatLng?>(null) }
    val locationPermissionState = rememberPermissionState(
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )
    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    val latLng = LatLng(it.latitude, it.longitude)
                    currentLocation = latLng

                    // Move a câmera para a localização do usuário
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(latLng, 15f)
                }
            }
        }else{
            // Request the location permission if it has not been granted
            locationPermissionState.launchPermissionRequest()
        }
    }
    Box(modifier = Modifier.fillMaxSize()){
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
            Clustering(items = listOf(

                MyClusterItem(singapore, "Singapore", "Marker in Singapore"),
                MyClusterItem(LatLng(1.45, 103.87), "Singapore", "Marker in Singapore"),
                MyClusterItem(LatLng(1.55, 103.87), "Singapore", "Marker in Singapore"),
                MyClusterItem(LatLng(1.65, 103.87), "Singapore", "Marker in Singapore"),
            )
            )
            markerPosition?.let {
                Marker(
                    state = MarkerState(position = it),
                    title = "Singapore",
                    snippet = "Marker in Singapore",
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.dangerous)
                )
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

data class MyClusterItem(
    private val position: LatLng,
    private val title: String,
    private val snippet: String
) : ClusterItem {
    override fun getPosition(): LatLng = position
    override fun getTitle(): String = title
    override fun getSnippet(): String = snippet
    override fun getZIndex(): Float = 0F
}
