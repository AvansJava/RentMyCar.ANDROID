package com.rentmycar.rentmycar.domain.mapper

import com.rentmycar.rentmycar.domain.model.Car
import com.rentmycar.rentmycar.domain.model.Location
import com.rentmycar.rentmycar.network.response.GetCarResponse
import com.rentmycar.rentmycar.network.response.GetCarResourceResponse
import com.rentmycar.rentmycar.network.response.GetLocationResponse

object CarMapper {

    //todo: carUser
    fun buildFrom(response: GetCarResponse, resources: List<GetCarResourceResponse>): Car {
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
            createdAt = response.createdAt,
            updatedAt = response.updatedAt,
            userId = response.userId
        )
    }

    fun buildFromWithLocation(response: GetCarResponse, location: Location, resources: List<GetCarResourceResponse>): Car {
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
            createdAt = response.createdAt,
            updatedAt = response.updatedAt,
            userId = response.userId
        )
    }
}