package com.rentmycar.rentmycar.domain.mapper

import com.rentmycar.rentmycar.domain.model.RentalPlan
import com.rentmycar.rentmycar.network.response.GetRentalPlanResponse

object RentalPlanMapper {
    fun buildFrom(response: GetRentalPlanResponse): RentalPlan {
        return RentalPlan(
            availableFrom = response.availableFrom,
            availableUntil = response.availableUntil,
            price = response.price,
            userId = response.userId,
            carId = response.carId,
            createdAt = response.createdAt,
            updatedAt = response.updatedAt,
            id = response.id
        )
    }
}