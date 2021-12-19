package com.rentmycar.rentmycar.network.request

class PostPaymentRequest(
    val reservation: ReservationNumberRequest,
    val paymentMethod: String
)