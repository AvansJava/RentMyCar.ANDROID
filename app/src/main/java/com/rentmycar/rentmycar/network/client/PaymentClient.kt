package com.rentmycar.rentmycar.network.client

import com.rentmycar.rentmycar.network.request.PostPaymentRequest
import com.rentmycar.rentmycar.network.response.PostPaymentResponse
import com.rentmycar.rentmycar.network.response.SimpleResponse
import com.rentmycar.rentmycar.network.service.PaymentService

class PaymentClient(
    private val paymentService: PaymentService
): BaseClient() {

    suspend fun postPayment(payment: PostPaymentRequest): SimpleResponse<PostPaymentResponse> {
        return safeApiCall { paymentService.postPayment(payment) }
    }
}