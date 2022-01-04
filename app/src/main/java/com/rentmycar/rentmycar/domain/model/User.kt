package com.rentmycar.rentmycar.domain.model

import com.google.android.material.textfield.TextInputEditText
import com.squareup.moshi.Json

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