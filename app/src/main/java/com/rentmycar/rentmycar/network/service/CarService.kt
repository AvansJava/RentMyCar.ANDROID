package com.rentmycar.rentmycar.network.service

import com.rentmycar.rentmycar.domain.model.Car
import com.rentmycar.rentmycar.network.response.GetCarResponse
import com.rentmycar.rentmycar.network.response.GetCarResourceResponse
import retrofit2.Response
import retrofit2.http.*

interface CarService {

    @GET("rent/list/")
    suspend fun getCarsList(): Response<List<GetCarResponse>>

    @GET("rent/{id}/")
    suspend fun getCarById(
        @Path("id") id: Int
    ): Response<GetCarResponse>

    @GET("cars/{id}/resource")
    suspend fun getCarResources(
        @Path("id") id: Int
    ): Response<List<GetCarResourceResponse>>

    @GET("cars/")
    suspend fun getCarsByUser(): Response<List<GetCarResponse>>

    @POST("cars/")
    suspend fun postCarWithLocation(
        @Body car: Car
    ): Response<GetCarResponse>

    @PUT("cars/{id}/")
    suspend fun putCar(
        @Path("id") id: Int,
        @Body car: Car
    ): Response<GetCarResponse>
}