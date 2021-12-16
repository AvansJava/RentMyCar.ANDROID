package com.rentmycar.rentmycar.domain.mapper

import com.rentmycar.rentmycar.domain.model.Car
import com.rentmycar.rentmycar.domain.model.RentalPlan
import com.rentmycar.rentmycar.network.response.GetRentalPlanResponse

object RentalPlanMapper {
    fun buildFrom(response: GetRentalPlanResponse, car: Car?): RentalPlan {
        return RentalPlan(
            availableFrom = response.availableFrom,
            availableUntil = response.availableUntil,
            price = response.price,
            userId = response.userId,
            car = car,
            createdAt = response.createdAt,
            updatedAt = response.updatedAt,
            id = response.id
        )
    }
}