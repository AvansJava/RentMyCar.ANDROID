package com.rentmycar.rentmycar.network.client

import com.rentmycar.rentmycar.network.response.GetInsuranceTypesResponse
import com.rentmycar.rentmycar.network.response.SimpleResponse
import com.rentmycar.rentmycar.network.service.InsuranceService

class InsuranceClient(
    private val insuranceService: InsuranceService
): BaseClient() {

    suspend fun getInsuranceTypes(): SimpleResponse<List<GetInsuranceTypesResponse>> {
        return safeApiCall { insuranceService.getInsuranceTypes() }
    }
}