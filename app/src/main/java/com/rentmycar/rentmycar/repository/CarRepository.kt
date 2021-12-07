package com.rentmycar.rentmycar.repository;

import com.rentmycar.rentmycar.domain.mapper.CarMapper
import com.rentmycar.rentmycar.domain.model.Car
import com.rentmycar.rentmycar.network.NetworkLayer;
import com.rentmycar.rentmycar.network.response.GetCarByIdResponse;
import com.rentmycar.rentmycar.network.response.GetCarResourceResponse

class CarRepository {

    private val locationRepository = LocationRepository()

    suspend fun getCarList(): MutableList<Car>? {
        val carList = mutableListOf<Car>()
        val request = NetworkLayer.carClient.getCarList()

        if (request.failed || !request.isSuccessful) {
            return null
        }

        request.body.forEach { item ->

            if (item.locationId != null) {
                val location = locationRepository.getLocationById(item.locationId)
            }

            val resource = getCarResourcesByCar(item.id)
            val car: Car = CarMapper.buildFrom(
                response = item,
                resources = resource
            )
            carList.add(car)
        }
        return carList
    }

    suspend fun getCarById(id: Int): Car? {
        val request = NetworkLayer.carClient.getCarById(id)

        if (request.failed || !request.isSuccessful) {
            return null
        }

        val resources = getCarResourcesByCar(request.body.id)

        if (request.body.locationId != null) {
            val location = locationRepository.getLocationById(request.body.locationId!!)
        }

        return CarMapper.buildFrom(
            response = request.body,
            resources = resources,
        )
    }

    private suspend fun getCarResourcesByCar(carId: Int): List<GetCarResourceResponse> {

        val request = NetworkLayer.carClient.getCarResources(carId)
        if (request.failed || !request.isSuccessful) {
            return emptyList()
        }

        return request.body
    }
}
