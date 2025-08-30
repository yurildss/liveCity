package com.example.livecity.model

import kotlinx.serialization.Serializable

@Serializable
data class GeocodingResponse(
    val results: List<Address>,
    val status: String
)
