package com.rentmycar.rentmycar.network.service

import com.rentmycar.rentmycar.network.response.GetCarByIdResponse
import retrofit2.Response
import retrofit2.http.GET

interface CarService {

    @GET("rent/list/")
    suspend fun getCarsList(): Response<List<GetCarByIdResponse>>
}