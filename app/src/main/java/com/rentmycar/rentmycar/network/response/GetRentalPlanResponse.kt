package com.rentmycar.rentmycar.network.response

import java.math.BigDecimal

data class GetRentalPlanResponse(
    val id: Int,
    val availableFrom: String,
    val availableUntil: String,
    val price: BigDecimal,
    val userId: Int,
    val carId: Int,
    val createdAt: String,
    val updatedAt: String
)
