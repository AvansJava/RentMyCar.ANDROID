package com.rentmycar.rentmycar.network.service

import com.rentmycar.rentmycar.network.response.GetTimeslotResponse
import retrofit2.Response
import retrofit2.http.GET

interface AvailabilityService {

    @GET("timeslot/")
    suspend fun getDefaultTimeslots(): Response<List<GetTimeslotResponse>>
}