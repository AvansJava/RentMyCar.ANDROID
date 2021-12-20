package com.rentmycar.rentmycar.domain.model

data class Availability(
    val productId: Int?,
    val status: String,
    val startAt: String,
    val endAt: String
)
