package com.rentmycar.rentmycar.network.response

data class GetTimeslotResponse(
    val id: Int,
    val startAt: String,
    val EndAt: String
)
