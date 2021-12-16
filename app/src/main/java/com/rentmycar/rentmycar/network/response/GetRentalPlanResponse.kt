package com.rentmycar.rentmycar.network.response

data class GetRentalPlanResponse(
    val id: Int,
    val availableFrom: String,
    val availableUntil: String,
    val price: Double,
    val userId: Int,
    val carId: Int,
    val createdAt: String,
    val updatedAt: String?
)
