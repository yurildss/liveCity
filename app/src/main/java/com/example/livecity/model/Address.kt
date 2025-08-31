package com.example.livecity.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Address(
    @SerialName(value = "address_components")
    val addressComponents: List<AddressComponent>,
    @SerialName(value = "formatted_address")
    val formattedAddress: String
)

@Serializable
data class AddressComponent(
    val longName: String,
    val shortName: String,
    val types: List<String>
)