package com.rentmycar.rentmycar.network.service

import com.rentmycar.rentmycar.network.request.PutTimeslotRequest
import com.rentmycar.rentmycar.network.response.GetAvailabilityPageResponse
import com.rentmycar.rentmycar.network.response.GetAvailabilityResponse
import com.rentmycar.rentmycar.network.response.GetTimeslotResponse
import retrofit2.Response
import retrofit2.http.*

interface AvailabilityService {

    @GET("timeslot/")
    suspend fun getDefaultTimeslots(): Response<List<GetTimeslotResponse>>

    @GET("rent/{carId}/availability/")
    suspend fun getCarAvailability(
        @Path("carId") carId: Int,
        @Query("pageSize") pageSize: Int,
        @Query("pageNumber") pageNumber: Int,
    ): Response<GetAvailabilityPageResponse>

    @PUT("availability/{id}/")
    suspend fun updateTimeslotStatus(
        @Path("id") id: Int,
        @Body status: PutTimeslotRequest
    ): Response<GetAvailabilityResponse>
}