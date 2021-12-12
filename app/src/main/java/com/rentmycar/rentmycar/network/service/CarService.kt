package com.rentmycar.rentmycar.network.service

import com.rentmycar.rentmycar.network.response.GetCarResponse
import com.rentmycar.rentmycar.network.response.GetCarResourceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

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
}