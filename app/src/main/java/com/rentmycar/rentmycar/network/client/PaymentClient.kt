package com.rentmycar.rentmycar.network.client

import com.rentmycar.rentmycar.network.request.PostPaymentCallbackRequest
import com.rentmycar.rentmycar.network.request.PostPaymentRequest
import com.rentmycar.rentmycar.network.response.GetPaymentResponse
import com.rentmycar.rentmycar.network.response.GetReservationResponse
import com.rentmycar.rentmycar.network.response.SimpleResponse
import com.rentmycar.rentmycar.network.service.PaymentService

class PaymentClient(
    private val paymentService: PaymentService
): BaseClient() {

    suspend fun postPayment(payment: PostPaymentRequest): SimpleResponse<GetPaymentResponse> {
        return safeApiCall { paymentService.postPayment(payment) }
    }

    suspend fun getPayment(id: Int): SimpleResponse<GetPaymentResponse> {
        return safeApiCall { paymentService.getPayment(id) }
    }

    suspend fun postPaymentCallback(id: Int, callback: PostPaymentCallbackRequest): SimpleResponse<String> {
        return safeApiCall { paymentService.postPaymentCallback(id, callback) }
    }
}