package com.rentmycar.rentmycar.network.service

import com.rentmycar.rentmycar.network.response.GetInsuranceTypesResponse
import retrofit2.Response
import retrofit2.http.GET

interface InsuranceService {
    @GET("insurance/")
    suspend fun getInsuranceTypes(): Response<List<GetInsuranceTypesResponse>>
}