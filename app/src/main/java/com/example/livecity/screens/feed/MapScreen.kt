package com.example.livecity.screens.feed

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import kotlinx.coroutines.launch

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
                        listOfAlerts = uiState.listOfEvaluations,
                        onBottomSheetClick = viewModel::changeBottomSheetState,
                        showBottomSheet = uiState.showBottomSheet,
                        actualItem = uiState.actualItem,
                        onClusterItemClick = viewModel::onClusterItemClick
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

@OptIn(MapsComposeExperimentalApi::class, ExperimentalPermissionsApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun Map(
     actualItem: MyClusterItem?,
     listOfAlerts: List<MyClusterItem>,
     onClusterItemClick: (MyClusterItem) -> Unit,
     onBottomSheetClick: () -> Unit,
     showBottomSheet: Boolean
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

                clusterManager.addItems(listOfAlerts)
                clusterManager.setOnClusterClickListener(
                    ClusterManager.OnClusterClickListener { cluster ->
                        val center = cluster.position
                        cameraPositionState.position = CameraPosition.fromLatLngZoom(center, 15f)
                        true
                    }
                )
                clusterManager.setOnClusterItemClickListener(
                    ClusterManager.OnClusterItemClickListener { item ->
                        onBottomSheetClick()
                        onClusterItemClick(item)
                        true
                    }
                )

                clusterManager.cluster()

                map.setOnCameraIdleListener(clusterManager)
                map.setOnMarkerClickListener(clusterManager)
            }
            if(showBottomSheet){
                ShowInfosClusterItem(actualItem,onBottomSheetClick)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowInfosClusterItem(
    actualItem: MyClusterItem?,
    showBottomSheet: ()-> Unit
){
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = showBottomSheet,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                actualItem?.title ?: "",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.padding(10.dp)
            )
            Text(
                actualItem?.snippet ?: "",
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.padding(10.dp)
            )
            Text(
                actualItem?.formattedAddress ?: "",
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.padding(10.dp)
            )
            Text(
                actualItem?.date ?: "",
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                fontFamily = FontFamily.Monospace,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(10.dp)
            )
            HorizontalDivider()
            Text(
                text = "Has it already been resolved?",
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.padding(10.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(Color.Black),
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                ) {
                    Text(text = "Yes")
                }
                OutlinedButton(
                    onClick = {},
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                ) {
                    Text(text = "Still a mess", color = Color.Black)
                }
            }

        }
    }
}