package com.rentmycar.rentmycar.network.response

data class GetAvailabilityResponse(
    val id: Int,
    val carId: Int,
    val rentalPlanId: Int,
    val productId: Int?,
    val timeslotId: Int,
    val status: String,
    val startAt: String,
    val endAt: String,
    val createdAt: String,
    val updatedAt: String?
)
