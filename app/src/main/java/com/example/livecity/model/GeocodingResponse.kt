package com.example.livecity.model
import com.google.gson.annotations.SerializedName


data class GeocodingResponse(
    @SerializedName("results") val results: List<Result>,
    @SerializedName("status") val status: String
)

data class Result(
    @SerializedName("formatted_address") val formattedAddress: String,
    @SerializedName("geometry") val geometry: Geometry,
    @SerializedName("place_id") val placeId: String
)

data class Geometry(
    @SerializedName("location") val location: Location
)

data class Location(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double
)
