package com.rentmycar.rentmycar.domain.model

data class Product(
    val id: Int,
    val reservationNumber: String?,
    val price: Double,
    val rentalPlanId: Int,
    val insuranceTypeId: String?,
    val insurancePrice: Double,
    val status: String?,
    val createdAt: String?,
    val updatedAt: String?
)
