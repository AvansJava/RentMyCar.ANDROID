package com.rentmycar.rentmycar.network.service

import com.rentmycar.rentmycar.network.response.GetLocationByIdResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface LocationService {

    @GET("location/{id}/")
    suspend fun getLocationById(
        @Path("id") id: Int
    ): Response<GetLocationByIdResponse>
}