package com.rentmycar.rentmycar.network.service

import com.rentmycar.rentmycar.network.request.PostPaymentRequest
import com.rentmycar.rentmycar.network.response.PostPaymentResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PaymentService {

    @POST("payment/")
    suspend fun postPayment(
        @Body payment: PostPaymentRequest
    ): Response<PostPaymentResponse>

}