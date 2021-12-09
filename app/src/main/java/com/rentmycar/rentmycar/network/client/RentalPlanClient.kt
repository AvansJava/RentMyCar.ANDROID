package com.rentmycar.rentmycar.network.client

import com.rentmycar.rentmycar.network.response.GetRentalPlanResponse
import com.rentmycar.rentmycar.network.response.SimpleResponse
import com.rentmycar.rentmycar.network.service.RentalPlanService

class RentalPlanClient(
    private val rentalPlanService: RentalPlanService
): BaseClient() {

    suspend fun getRentalPlanById(id: Int): SimpleResponse<GetRentalPlanResponse> {
        return safeApiCall { rentalPlanService.getRentalPlanById(id) }
    }

    suspend fun getRentalPlanByCar(carId: Int): SimpleResponse<GetRentalPlanResponse> {
        return safeApiCall { rentalPlanService.getRentalPlanByCar(carId) }
    }
}