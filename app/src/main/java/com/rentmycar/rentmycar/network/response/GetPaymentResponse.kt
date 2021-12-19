package com.rentmycar.rentmycar.network.response

data class GetPaymentResponse(
    val id: Int,
    val reservationNumber: String,
    val userId: Int,
    val price: Double,
    val paymentStatus: String,
    val paymentMethod: String,
    val paidAt: String?,
    val createdAt: String,
    val updatedAt: String
)
