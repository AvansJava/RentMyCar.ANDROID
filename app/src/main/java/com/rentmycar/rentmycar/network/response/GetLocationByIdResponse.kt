package com.rentmycar.rentmycar.network.response

data class GetLocationByIdResponse(
    val id: Int,
    val street: String,
    val houseNumber: String,
    val postalCode: String,
    val city: String,
    val country: String,
    val latitude: Float,
    val longitude: Float,
    val userId: Long,
    val createdAt: String,
    val updatedAt: String
)
