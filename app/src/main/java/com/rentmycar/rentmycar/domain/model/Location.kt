package com.rentmycar.rentmycar.domain.model

data class Location(
    val street: String?,
    val houseNumber: String?,
    val postalCode: String?,
    val city: String?,
    val country: String?,
    val latitude: Double,
    val longitude: Double,
    val id: Int?,
    val userId: Int?
)
