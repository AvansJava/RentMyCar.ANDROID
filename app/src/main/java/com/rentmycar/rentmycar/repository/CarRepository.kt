package com.rentmycar.rentmycar.repository;

import android.util.Log
import com.rentmycar.rentmycar.domain.mapper.CarMapper
import com.rentmycar.rentmycar.domain.model.Car
import com.rentmycar.rentmycar.network.NetworkLayer;
import com.rentmycar.rentmycar.network.response.GetCarResourceResponse

class CarRepository {

    private val locationRepository = LocationRepository()

    suspend fun getCarList(): MutableList<Car> {
        val carList = mutableListOf<Car>()
        val request = NetworkLayer.carClient.getCarList()

        if (request.failed || !request.isSuccessful) {
            return carList
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

        if (request.body.locationId != null) {
            val location = locationRepository.getLocationById(request.body.locationId!!)
        }

        val resourceRequest = getCarResourcesByCar(request.body.id)

        return CarMapper.buildFrom(
            response = request.body,
            resources = resourceRequest,
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
