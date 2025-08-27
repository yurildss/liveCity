package com.example.livecity.screens.alert

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livecity.R
import com.example.livecity.model.Evaluation
import com.example.livecity.model.Type
import com.example.livecity.service.module.StorageService
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlertScreenViewModel @Inject constructor(
    private val storageService: StorageService
) : ViewModel()  {

    private val _uiState = MutableStateFlow(AlertScreenUIState())
    val uiState: StateFlow<AlertScreenUIState> = _uiState.asStateFlow()

    fun setTitle(title: String){
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun setDescription(description: String){
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun setType(type: Pair<String, Int>){
        _uiState.value = _uiState.value.copy(type = type)
    }

    fun setPosition(position: Pair<Double, Double>){
        _uiState.value = _uiState.value.copy(position = GeoPoint(position.first, position.second))
    }

    fun updateExpanded(){
        _uiState.value = _uiState.value.copy(expanded = !_uiState.value.expanded)
    }

    fun onMapLoaded(){
        _uiState.value = _uiState.value.copy(isLoaded = true)
    }

    fun setCurrentLocation(location: LatLng){
        _uiState.value = _uiState.value.copy(currentLocation = location)
    }

    fun setMarkerPositionSelectedByUser(location: LatLng){
        _uiState.value = _uiState.value.copy(markerPositionSelectedByUser = location)
    }

    fun setUseMyLocation(useMyLocation: Boolean){
        _uiState.value = _uiState.value.copy(useMyLocation = useMyLocation)
    }

    fun setUseSetLocation(useSetLocation: Boolean){
        _uiState.value = _uiState.value.copy(useSetLocation = useSetLocation)
    }

    fun setExpandedGoogleMaps(){
        _uiState.value = _uiState.value.copy(expandedGoogleMaps = !_uiState.value.expandedGoogleMaps)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveAlert(onSaved: () -> Unit){
        viewModelScope.launch {
            if (_uiState.value.title.isBlank()){
                return@launch
            }
            if (_uiState.value.description.isBlank()){
                return@launch
            }
            if(_uiState.value.type.first.isBlank()){
                return@launch
            }
            val alert = Evaluation(id = "", title = _uiState.value.title, description = _uiState.value.description, date = Timestamp.now(), type = _uiState.value.type.toType(), position = if (_uiState.value.useMyLocation) {
                GeoPoint(_uiState.value.currentLocation!!.latitude,_uiState.value.currentLocation!!.longitude)
                } else {
                GeoPoint(_uiState.value.markerPositionSelectedByUser!!.latitude,_uiState.value.markerPositionSelectedByUser!!.longitude)
                }, userId = "", dateClose = null, closed = false)

            storageService.saveAlert(alert)
            onSaved()
            }

        }


    fun Pair<String, Int>.toType(): Type {
        return Type(
            alertType = first,
            alertImage = second
        )
    }
}

data class AlertScreenUIState(
    val title: String = "",
    val description: String = "",
    val type: Pair<String, Int> = "" to 0,
    val position: GeoPoint? = null,
    val userId: String = "",
    val listOfAlerts: List<Pair<String, Int>> = listOf(
        Pair<String, Int>("Dangerous area", R.drawable.dangerous),
        Pair<String, Int>("Building", R.drawable.build_50dp_ea3323_fill0_wght400_grad0_opsz48),
        Pair<String, Int>("Car crash", R.drawable.car_crash_50dp_ea3323_fill0_wght400_grad0_opsz48),
        Pair<String, Int>("Rain/Snow/Storm", R.drawable.thunderstorm_50dp_ea3323_fill0_wght400_grad0_opsz48),
    ),
    val expanded: Boolean = false,
    val isLoaded: Boolean = false,
    val currentLocation: LatLng? = null,
    val markerPositionSelectedByUser: LatLng? = null,
    val useMyLocation: Boolean = false,
    val useSetLocation: Boolean = false,
    val expandedGoogleMaps: Boolean = false
)