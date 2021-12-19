package com.rentmycar.rentmycar.network.response

import com.rentmycar.rentmycar.domain.model.Product

data class GetReservationResponse(
    val reservationNumber: String,
    val userId: Int,
    val price: Double,
    val status: String,
    val paidAt: String?,
    val product: Product,
    val createdAt: String,
    val updatedAt: String?
)
