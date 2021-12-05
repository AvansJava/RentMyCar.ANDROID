package com.rentmycar.rentmycar.network.response

data class GetUserResponse(
    val firstName: String,
    val lastName: String,
    val street: String,
    val houseNumber: String,
    val postalCode: String,
    val city: String,
    val country: String,
    val phoneNumber: String,
    val iban: String,
    val email: String,
)
