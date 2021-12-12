package com.rentmycar.rentmycar.domain.model

import com.rentmycar.rentmycar.network.response.GetLocationResponse

data class Car (
    val id: Int,
    val brand: String,
    val brandType: String,
    val model: String,
    val licensePlateNumber: String,
    val consumption: Double,
    val costPrice: Int,
    val carType: String,
    val resources: List<CarResource>,
    val location: Location?,
    val createdAt: String,
    val updatedAt: String?
)