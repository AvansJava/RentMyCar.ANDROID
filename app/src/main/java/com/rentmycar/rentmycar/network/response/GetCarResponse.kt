package com.rentmycar.rentmycar.network.response

data class GetCarResponse(
    val id: Int,
    val brand: String,
    val brandType: String,
    val model: String,
    val licensePlateNumber: String,
    val consumption: Double,
    val costPrice: Int,
    val carType: String,
    val userId: Int,
    val locationId: Int?,
    val createdAt: String,
    val updatedAt: String?
)
