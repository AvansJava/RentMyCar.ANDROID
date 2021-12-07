package com.rentmycar.rentmycar.network.response

data class PostLoginResponse(
    val firstName: String,
    val lastName: String,
    val accessToken: String,
    val userId: String
)
