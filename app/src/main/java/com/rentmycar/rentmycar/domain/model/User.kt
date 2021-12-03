package com.rentmycar.rentmycar.domain.model

data class User(
    val city: String,
    val country: String,
    val email: String,
    val firstName: String,
    val houseNumber: String,
    val iban: String,
    val lastName: String,
    val phoneNumber: String,
    val postalCode: String,
    val street: String
)