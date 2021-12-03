package com.rentmycar.rentmycar.network.response

import java.time.LocalDateTime

data class GetLocationByIdResponse(
    val id: Long,
    val street: String,
    val houseNumber: String,
    val postalCode: String,
    val city: String,
    val country: String,
    val latitude: Float,
    val longitude: Float,
    val userId: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
