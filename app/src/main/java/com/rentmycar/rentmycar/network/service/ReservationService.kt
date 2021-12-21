package com.rentmycar.rentmycar.network.service

import com.rentmycar.rentmycar.network.request.PostReservationRequest
import com.rentmycar.rentmycar.network.response.GetAvailabilityResponse
import com.rentmycar.rentmycar.network.response.GetReservationResponse
import retrofit2.Response
import retrofit2.http.*

interface ReservationService {

    @POST("reservation/")
    suspend fun postReservation(
        @Body reservation: PostReservationRequest
    ): Response<GetReservationResponse>

    @GET("reservation/{reservationNumber}/availability")
    suspend fun getTimeslotsByReservation(
        @Path("reservationNumber") reservationNumber: String
    ): Response<List<GetAvailabilityResponse>>

    @GET("reservation/{reservationNumber}/")
    suspend fun getReservation(
        @Path("reservationNumber") reservationNumber: String
    ): Response<GetReservationResponse>

    @GET("reservation/")
    suspend fun getReservationList(
        @Query("status") status: String?,
    ): Response<List<GetReservationResponse>>
}