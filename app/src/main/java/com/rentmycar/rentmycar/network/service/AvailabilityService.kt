package com.rentmycar.rentmycar.network.service

import com.rentmycar.rentmycar.network.response.GetAvailabilityResponse
import com.rentmycar.rentmycar.network.response.GetTimeslotResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AvailabilityService {

    @GET("timeslot/")
    suspend fun getDefaultTimeslots(): Response<List<GetTimeslotResponse>>

    @GET("rent/{carId}/availability/")
    suspend fun getCarAvailability(
        @Path("carId") carId: Int
    ): Response<List<GetAvailabilityResponse>>
}