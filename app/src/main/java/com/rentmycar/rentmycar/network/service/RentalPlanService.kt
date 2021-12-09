package com.rentmycar.rentmycar.network.service

import com.rentmycar.rentmycar.network.response.GetRentalPlanResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RentalPlanService {

    @GET("rentalplan/{id}/")
    suspend fun getRentalPlanById(
        @Path("id") id: Int
    ): Response<GetRentalPlanResponse>

    @GET("rentalplan/car/{carId}/")
    suspend fun getRentalPlanByCar(
        @Path("carId") carId: Int
    ): Response<GetRentalPlanResponse>

}