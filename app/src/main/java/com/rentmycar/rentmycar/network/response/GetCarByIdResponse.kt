package com.rentmycar.rentmycar.network.response

import java.time.LocalDateTime

data class GetCarByIdResponse(
    val id: Long,
    val brand: String,
    val BrandType: String,
    val model: String,
    val licensePlateNumber: String,
    val consumption: Double,
    val costPrice: Int,
    val carType: String,
    val userId: Long,
    val location: GetLocationByIdResponse,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
