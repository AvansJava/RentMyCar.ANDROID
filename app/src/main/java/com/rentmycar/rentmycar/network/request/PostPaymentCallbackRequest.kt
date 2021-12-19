package com.rentmycar.rentmycar.network.request

data class PostPaymentCallbackRequest(
    val externalReference: String,
    val status: String
)
