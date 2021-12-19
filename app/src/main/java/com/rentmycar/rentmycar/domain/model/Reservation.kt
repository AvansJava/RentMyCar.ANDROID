package com.rentmycar.rentmycar.domain.model


data class Reservation(
    val reservationNumber: String?,
    val price: Double,
    val status: String?,
    val paidAt: String?,
    val product: Product?
)
