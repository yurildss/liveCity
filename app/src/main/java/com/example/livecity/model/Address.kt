package com.example.livecity.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Address(
    @SerialName(value = "formatted_address")
    val formattedAddress: String
)
