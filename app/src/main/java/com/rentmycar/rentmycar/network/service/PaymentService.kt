package com.rentmycar.rentmycar.network.service

import com.rentmycar.rentmycar.network.request.PostPaymentCallbackRequest
import com.rentmycar.rentmycar.network.request.PostPaymentRequest
import com.rentmycar.rentmycar.network.response.GetPaymentResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PaymentService {

    @POST("payment/")
    suspend fun postPayment(
        @Body payment: PostPaymentRequest
    ): Response<GetPaymentResponse>

    @GET("payment/{id}/")
    suspend fun getPayment(
        @Path("id") id: Int
    ): Response<GetPaymentResponse>

    @POST("payment/{id}/callback/")
    suspend fun postPaymentCallback(
        @Path("id") id: Int,
        @Body callback: PostPaymentCallbackRequest
    ): Response<String>
}