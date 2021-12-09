package com.rentmycar.rentmycar.domain.model

import java.math.BigDecimal

data class RentalPlan(
    val id: Int?,
    val availableFrom: String,
    val availableUntil: String,
    val price: BigDecimal,
    val userId: Int,
    val carId: Int,
    val createdAt: String,
    val updatedAt: String?
)