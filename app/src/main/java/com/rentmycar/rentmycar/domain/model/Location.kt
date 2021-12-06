package com.rentmycar.rentmycar.domain.model

data class Location(
    val id: Long,
    val street: String,
    val houseNumber: String,
    val postalCode: String,
    val city: String,
    val country: String,
    val latitude: Float,
    val longitude: Float,
)
