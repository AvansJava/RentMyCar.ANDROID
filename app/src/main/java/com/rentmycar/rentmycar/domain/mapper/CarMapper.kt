package com.rentmycar.rentmycar.domain.mapper

import com.rentmycar.rentmycar.domain.model.Car
import com.rentmycar.rentmycar.domain.model.CarUser
import com.rentmycar.rentmycar.domain.model.Location
import com.rentmycar.rentmycar.network.response.GetCarByIdResponse
import com.rentmycar.rentmycar.network.response.GetCarResourceResponse

object CarMapper {

    //todo: carUser
    fun buildFrom(response: GetCarByIdResponse, resources: List<GetCarResourceResponse>): Car {
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
            createdAt = response.createdAt,
            updatedAt = response.updatedAt
        )
    }
}