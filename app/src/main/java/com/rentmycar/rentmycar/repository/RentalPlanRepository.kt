package com.rentmycar.rentmycar.repository

import com.rentmycar.rentmycar.domain.mapper.RentalPlanMapper
import com.rentmycar.rentmycar.domain.model.RentalPlan
import com.rentmycar.rentmycar.network.NetworkLayer

class RentalPlanRepository {

    suspend fun getRentalPlanById(id: Int): RentalPlan? {
        val request = NetworkLayer.rentalPlantClient.getRentalPlanById(id)

        if (request.failed || !request.isSuccessful) {
            return null
        }

        return RentalPlanMapper.buildFrom(response = request.body)
    }

    suspend fun getRentalPlanByCar(carId: Int): RentalPlan? {
        val request = NetworkLayer.rentalPlantClient.getRentalPlanByCar(carId)

        if (request.failed || !request.isSuccessful) {
            return null
        }

        return RentalPlanMapper.buildFrom(response = request.body)
    }
}