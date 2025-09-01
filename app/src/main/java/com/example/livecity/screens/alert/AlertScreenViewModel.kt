package com.example.livecity.screens.alert

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livecity.R
import com.example.livecity.model.Evaluation
import com.example.livecity.model.GeocodingResponse
import com.example.livecity.model.Type
import com.example.livecity.network.GeoCodingApi
import com.example.livecity.service.AccountService
import com.example.livecity.service.module.StorageService
import com.example.livecity.util.getIconResByName
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
    private val storageService: StorageService,
    private val accountService: AccountService
) : ViewModel()  {

    val currentUserId = accountService.currentUserId

    private val _uiState = MutableStateFlow(AlertScreenUIState())
    val uiState: StateFlow<AlertScreenUIState> = _uiState.asStateFlow()

    suspend fun getFormattedAddress(): String{

        var formattedAddress : GeocodingResponse

        if (_uiState.value.useMyLocation) {
            formattedAddress = GeoCodingApi.geocodingService.getAddressByGeo("${_uiState.value.currentLocation!!.latitude},${_uiState.value.currentLocation!!.longitude}")
            return formattedAddress.results[0].formattedAddress
        } else {
            formattedAddress = GeoCodingApi.geocodingService.getAddressByGeo("${_uiState.value.markerPositionSelectedByUser!!.latitude},${_uiState.value.markerPositionSelectedByUser!!.longitude}")
            return formattedAddress.results[0].formattedAddress
        }

    }

    fun setTitle(title: String){
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun setDescription(description: String){
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun setType(type: Pair<String, String>){
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
                _uiState.value = _uiState.value.copy(message = "Title cannot be empty")
                return@launch
            }
            if (_uiState.value.description.isBlank()){
                _uiState.value = _uiState.value.copy(message = "Description cannot be empty")
                return@launch
            }
            if(_uiState.value.type.first.isBlank()){
                _uiState.value = _uiState.value.copy(message = "Type cannot be empty")
                return@launch
            }

            if(!_uiState.value.useMyLocation && !_uiState.value.useSetLocation){
                _uiState.value = _uiState.value.copy(message = "Location cannot be empty")
                return@launch
            }

            val formatedAddress = getFormattedAddress()

            val alert = Evaluation(
                title = _uiState.value.title,
                description = _uiState.value.description,
                date = Timestamp.now(), type = _uiState.value.type.toType(),
                position = if (_uiState.value.useMyLocation) {
                GeoPoint(_uiState.value.currentLocation!!.latitude,_uiState.value.currentLocation!!.longitude)
                } else {
                GeoPoint(_uiState.value.markerPositionSelectedByUser!!.latitude,_uiState.value.markerPositionSelectedByUser!!.longitude)
                },
                userId = currentUserId,
                dateClose = null, closed = false,
                formattedAddress = formatedAddress
            )

            storageService.saveAlert(alert)
            onSaved()
            }

        }


    fun Pair<String, String>.toType(): Type {
        return Type(
            alertType = first,
            alertImage = second
        )
    }
}

data class AlertScreenUIState(
    val title: String = "",
    val description: String = "",
    val type: Pair<String, String> = "" to "",
    val position: GeoPoint? = null,
    val userId: String = "",
    val listOfAlerts: List<Pair<String, String>> = listOf(
        Pair<String, String>("Dangerous area", "ic_danger"),
        Pair<String, String>("Building", "ic_building"),
        Pair<String, String>("Car crash", "ic_car_crash"),
        Pair<String, String>("Rain/Snow/Storm", "ic_rain_snow_storm"),
    ),
    val expanded: Boolean = false,
    val isLoaded: Boolean = false,
    val currentLocation: LatLng? = null,
    val markerPositionSelectedByUser: LatLng? = null,
    val useMyLocation: Boolean = false,
    val useSetLocation: Boolean = false,
    val expandedGoogleMaps: Boolean = false,
    val message: String = ""
)