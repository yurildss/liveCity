package com.example.livecity.screens.feed

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.livecity.R
import com.example.livecity.model.Evaluation
import com.example.livecity.screens.account.AccountScreen
import com.example.livecity.screens.alert.AlertScreen
import com.example.livecity.util.MyClusterRenderer
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.clustering.Clustering
import com.google.maps.android.compose.rememberCameraPositionState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FeedMapScreen(
    onAlertSaved: () -> Unit,
    onLogOut: () -> Unit,
    onMyAlertsClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MapScreenViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar{
                uiState.navItems.forEach {
                    NavigationBarItem(
                        selected = it.testTag == uiState.selectedNavItem.testTag,
                        onClick = { viewModel.onNavItemClicked(it) },
                        icon = { it.icon },
                        label = { Text(text = it.description) }
                    )
                }
            }
        },
        content = {
            Box(modifier = Modifier.padding(it)){
                when(uiState.selectedNavItem.testTag){
                    "homeScreen" -> Map(
                        listOfAlerts = uiState.listOfEvaluations
                    )
                    "searchScreen" -> Text(text = "Search")
                    "addScreen" -> AlertScreen(
                        onSaved = onAlertSaved
                    )
                    "accountScreen" -> AccountScreen(
                        onLogOut = onLogOut,
                        onMyAlertsClick = onMyAlertsClick
                    )
                }
            }
        },
    )
}

@OptIn(MapsComposeExperimentalApi::class, ExperimentalPermissionsApi::class)
@Composable
fun Map(
     listOfAlerts: List<MyClusterItem>
){
    val context = LocalContext.current

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }
    var currentLocation by remember { mutableStateOf<LatLng?>(null) }
    val cameraPositionState = rememberCameraPositionState()

    val locationPermissionState = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION
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
            onMapLoaded = {            },
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                isMyLocationEnabled = true
            )
        ){
            MapEffect(listOfAlerts) { map ->
                val clusterManager = ClusterManager<MyClusterItem>(context, map)
                clusterManager.renderer = MyClusterRenderer(context, map, clusterManager)

                Log.d("Cluster", "Adicionando ${listOfAlerts.size} items")
                clusterManager.addItems(listOfAlerts)
                clusterManager.cluster()

                map.setOnCameraIdleListener(clusterManager)
                map.setOnMarkerClickListener(clusterManager)
            }


        }
    }
}

