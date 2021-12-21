package com.rentmycar.rentmycar.repository

import android.widget.Toast
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.domain.mapper.AvailabilityMapper
import com.rentmycar.rentmycar.domain.mapper.ReservationMapper
import com.rentmycar.rentmycar.domain.model.Availability
import com.rentmycar.rentmycar.domain.model.Reservation
import com.rentmycar.rentmycar.network.NetworkLayer
import com.rentmycar.rentmycar.network.request.PostReservationRequest
import com.rentmycar.rentmycar.network.response.GetAvailabilityResponse

class ReservationRepository {
    private fun client() = NetworkLayer.reservationClient

    suspend fun postReservation(reservation: PostReservationRequest): Reservation? {
        val request = client().postReservation(reservation)

        if (request.failed || !request.isSuccessful) {
            Toast.makeText(RentMyCarApplication.context, RentMyCarApplication.context.getString(R.string.error_post_reservation), Toast.LENGTH_LONG).show()
            return null
        }

        return ReservationMapper.buildFrom(
            response = request.body,
            product = request.body.product,
            availability = emptyList()
        )
    }

    suspend fun getTimeslotsByReservation(reservationNumber: String): List<GetAvailabilityResponse> {
        val request = client().getTimeslotsByReservation(reservationNumber)

        if (request.failed || !request.isSuccessful) {
            return emptyList()
        }
        return request.body
    }

    suspend fun getReservation(reservationNumber: String): Reservation? {
        val request = client().getReservation(reservationNumber)

        if (request.failed || !request.isSuccessful) {
            Toast.makeText(RentMyCarApplication.context,
                RentMyCarApplication.context.getString(R.string.error_get_reservation), Toast.LENGTH_LONG).show()
            return null
        }

        return ReservationMapper.buildFrom(
            response = request.body,
            product = request.body.product,
            availability = getTimeslotsByReservation(request.body.reservationNumber))
    }

    suspend fun getReservationList(status: String?): List<Reservation?> {
        val reservationList = mutableListOf<Reservation>()
        val request = client().getReservationList(status)

        if (request.failed || !request.isSuccessful) {
            return emptyList()
        }

        request.body.forEach { item ->

            val availability = getTimeslotsByReservation(item.reservationNumber)

            val reservation: Reservation = ReservationMapper.buildFrom(
                response = item,
                product = item.product,
                availability = availability
            )

            reservationList.add(reservation)
        }
        return reservationList
    }
}