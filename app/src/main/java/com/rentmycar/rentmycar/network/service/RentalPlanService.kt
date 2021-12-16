package com.rentmycar.rentmycar.network.service

import com.rentmycar.rentmycar.domain.model.RentalPlan
import com.rentmycar.rentmycar.network.request.PostRentalPlanRequest
import com.rentmycar.rentmycar.network.response.GetRentalPlanResponse
import retrofit2.Response
import retrofit2.http.*

interface RentalPlanService {
    @GET("rentalplan/")
    suspend fun getRentalPlans(): Response<List<GetRentalPlanResponse>>

    @GET("rentalplan/{id}/")
    suspend fun getRentalPlanById(
        @Path("id") id: Int
    ): Response<GetRentalPlanResponse>

    @GET("rentalplan/car/{carId}/")
    suspend fun getRentalPlanByCar(
        @Path("carId") carId: Int
    ): Response<GetRentalPlanResponse>

    @POST("rentalplan/")
    suspend fun postRentalPlan(
        @Body rentalPlan: PostRentalPlanRequest
    ): Response<GetRentalPlanResponse>

    @DELETE("rentalplan/{id}/")
    suspend fun deleteRentalPlan(
        @Path("id") id: Int
    ): Response<String>
}