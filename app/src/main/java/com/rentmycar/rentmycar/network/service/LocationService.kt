package com.rentmycar.rentmycar.network.service

import com.rentmycar.rentmycar.domain.model.Location
import com.rentmycar.rentmycar.network.response.GetLocationResponse
import retrofit2.Response
import retrofit2.http.*

interface LocationService {

    @GET("location/{id}/")
    suspend fun getLocationById(
        @Path("id") id: Int
    ): Response<GetLocationResponse>

    @POST("location/")
    suspend fun postLocation(@Body location: Location): Response<GetLocationResponse>

    @GET("location/")
    suspend fun getLocations(): Response<List<GetLocationResponse>>

    @PUT("location/{id}/")
    suspend fun updateLocationById(
        @Path("id") id: Int,
        @Body location: Location
    ): Response<GetLocationResponse>

    @DELETE("location/{id}/")
    suspend fun deleteLocationById(
        @Path("id") id: Int
    ): Response<String>
}