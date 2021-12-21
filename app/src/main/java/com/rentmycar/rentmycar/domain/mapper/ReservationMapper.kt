package com.rentmycar.rentmycar.domain.mapper

import com.rentmycar.rentmycar.domain.model.Availability
import com.rentmycar.rentmycar.domain.model.Product
import com.rentmycar.rentmycar.domain.model.Reservation
import com.rentmycar.rentmycar.network.response.GetAvailabilityResponse
import com.rentmycar.rentmycar.network.response.GetReservationResponse

object ReservationMapper {

    fun buildFrom(response: GetReservationResponse, product: Product, availability: List<GetAvailabilityResponse>): Reservation {
        return Reservation(
            reservationNumber = response.reservationNumber,
            price = response.price,
            status = response.status,
            paidAt = response.paidAt,
            product = product,
            availability = availability.map { AvailabilityMapper.buildFrom(it) },
        )
    }
}