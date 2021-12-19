package com.rentmycar.rentmycar.repository

import android.widget.Toast
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.domain.mapper.ReservationMapper
import com.rentmycar.rentmycar.domain.model.Reservation
import com.rentmycar.rentmycar.network.NetworkLayer
import com.rentmycar.rentmycar.network.request.PostPaymentRequest
import com.rentmycar.rentmycar.network.response.GetPaymentResponse

class PaymentRepository {
    private fun client() = NetworkLayer.paymentClient

    suspend fun postPayment(payment: PostPaymentRequest): GetPaymentResponse? {
        val request = client().postPayment(payment)

        if (request.failed || !request.isSuccessful) {
            Toast.makeText(RentMyCarApplication.context, RentMyCarApplication.context.getString(R.string.error_post_payment), Toast.LENGTH_LONG).show()
            return null
        }

        return request.body
    }

    suspend fun getPayment(id: Int): GetPaymentResponse? {
        val request = client().getPayment(id)

        if (request.failed || !request.isSuccessful) {
            Toast.makeText(RentMyCarApplication.context,
                RentMyCarApplication.context.getString(R.string.error_get_payment), Toast.LENGTH_LONG).show()
            return null
        }

        return request.body
    }
}