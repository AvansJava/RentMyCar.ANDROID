package com.rentmycar.rentmycar.network.service

import com.rentmycar.rentmycar.network.request.PostReservationRequest
import com.rentmycar.rentmycar.network.response.GetReservationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ReservationService {

    @POST("reservation/")
    suspend fun postReservation(
        @Body reservation: PostReservationRequest
    ): Response<GetReservationResponse>
}