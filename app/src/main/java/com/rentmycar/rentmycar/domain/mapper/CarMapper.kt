package com.rentmycar.rentmycar.domain.mapper

import com.rentmycar.rentmycar.domain.model.Car
import com.rentmycar.rentmycar.domain.model.Location
import com.rentmycar.rentmycar.domain.model.RentalPlan
import com.rentmycar.rentmycar.network.response.GetCarResponse
import com.rentmycar.rentmycar.network.response.GetCarResourceResponse
import com.rentmycar.rentmycar.network.response.GetLocationResponse

object CarMapper {

    fun buildFrom(response: GetCarResponse, rentalPlan: RentalPlan?, resources: List<GetCarResourceResponse>): Car {
        return Car(
            id = response.id,
            brand = response.brand,
            brandType = response.brandType,
            model = response.model,
            licensePlateNumber = response.licensePlateNumber,
            consumption = response.consumption,
            costPrice = response.costPrice,
            carType = response.carType,
            resources = resources.map { CarResourceMapper.buildFrom(it) },
            location = null,
            rentalPlan = rentalPlan,
            createdAt = response.createdAt,
            updatedAt = response.updatedAt,
            userId = response.userId
        )
    }

    fun buildFromWithLocation(response: GetCarResponse, rentalPlan: RentalPlan?, location: Location, resources: List<GetCarResourceResponse>): Car {
        return Car(
            id = response.id,
            brand = response.brand,
            brandType = response.brandType,
            model = response.model,
            licensePlateNumber = response.licensePlateNumber,
            consumption = response.consumption,
            costPrice = response.costPrice,
            carType = response.carType,
            resources = resources.map { CarResourceMapper.buildFrom(it) },
            location = location,
            rentalPlan = rentalPlan,
            createdAt = response.createdAt,
            updatedAt = response.updatedAt,
            userId = response.userId
        )
    }
}