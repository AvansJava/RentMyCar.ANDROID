package com.rentmycar.rentmycar.domain.model

import java.math.BigDecimal

data class RentalPlan(
    val id: Int?,
    val availableFrom: String,
    val availableUntil: String,
    val price: Double,
    val userId: Int,
    val car: Car?,
    val createdAt: String,
    val updatedAt: String?
)