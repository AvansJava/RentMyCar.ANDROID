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
            product = request.body.product
        )
    }

    suspend fun getTimeslotsByReservation(reservationNumber: String): List<Availability> {
        val timeslotList = mutableListOf<Availability>()
        val request = client().getTimeslotsByReservation(reservationNumber)

        if (request.failed || !request.isSuccessful) {
            return emptyList()
        }

        request.body.forEach { item ->
            val timeslot: Availability = AvailabilityMapper.buildFrom(
                response = item
            )
            timeslotList.add(timeslot)
        }
        return timeslotList
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
            product = request.body.product)
    }
}