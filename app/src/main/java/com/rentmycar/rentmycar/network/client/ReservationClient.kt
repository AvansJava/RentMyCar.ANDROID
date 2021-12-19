package com.rentmycar.rentmycar.network.client

import com.rentmycar.rentmycar.network.request.PostReservationRequest
import com.rentmycar.rentmycar.network.response.GetReservationResponse
import com.rentmycar.rentmycar.network.response.SimpleResponse
import com.rentmycar.rentmycar.network.service.ReservationService

class ReservationClient(
    private val reservationService: ReservationService
): BaseClient() {

    suspend fun postReservation(reservation: PostReservationRequest): SimpleResponse<GetReservationResponse> {
        return safeApiCall { reservationService.postReservation(reservation) }
    }
}