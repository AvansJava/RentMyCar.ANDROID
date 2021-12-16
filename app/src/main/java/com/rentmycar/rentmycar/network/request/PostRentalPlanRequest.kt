package com.rentmycar.rentmycar.network.request

data class PostRentalPlanRequest(
    val car: CarIdRequest,
    val availableFrom: String,
    val availableUntil: String,
    val price: Double
)