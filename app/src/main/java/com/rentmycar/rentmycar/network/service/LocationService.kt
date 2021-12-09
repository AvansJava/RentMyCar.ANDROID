package com.rentmycar.rentmycar.network.service

import com.rentmycar.rentmycar.domain.model.Location
import com.rentmycar.rentmycar.network.response.GetLocationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LocationService {

    @GET("location/{id}/")
    suspend fun getLocationById(
        @Path("id") id: Int
    ): Response<GetLocationResponse>

    @POST("location/")
    suspend fun postLocation(@Body location: Location): Response<GetLocationResponse>

}