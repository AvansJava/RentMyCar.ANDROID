package com.rentmycar.rentmycar.network.response

data class GetLocationResponse(
    val id: Int,
    val street: String?,
    val houseNumber: String?,
    val postalCode: String?,
    val city: String?,
    val country: String?,
    val latitude: Double,
    val longitude: Double,
    val userId: Int,
    val createdAt: String,
    val updatedAt: String?
)
