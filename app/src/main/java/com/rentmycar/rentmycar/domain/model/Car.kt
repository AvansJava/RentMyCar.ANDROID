package com.rentmycar.rentmycar.domain.model

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
    val createdAt: String,
    val updatedAt: String?
)