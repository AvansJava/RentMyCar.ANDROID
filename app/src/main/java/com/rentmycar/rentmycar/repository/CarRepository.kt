package com.rentmycar.rentmycar.repository;

import android.content.Context
import android.widget.Toast
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.domain.mapper.CarMapper
import com.rentmycar.rentmycar.domain.model.Car
import com.rentmycar.rentmycar.domain.model.Location
import com.rentmycar.rentmycar.network.NetworkLayer;
import com.rentmycar.rentmycar.network.response.GetCarResourceResponse
import com.rentmycar.rentmycar.room.RentMyCarDatabase
import com.rentmycar.rentmycar.room.Car as CarRoom

class CarRepository {

    private val locationRepository = LocationRepository()

    private fun client() = NetworkLayer.carClient
    private fun dao(context : Context) = RentMyCarDatabase.getInstance(context).carDao()

    suspend fun getCarList(): MutableList<Car> {
        val carList = mutableListOf<Car>()
        val request = client().getCarList()

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
        val request = client().getCarById(id)
        var location: Location? = null
        if (request.failed || !request.isSuccessful) {
            return null
        }

        if (request.body.locationId != null) {
            location = locationRepository.getLocationById(request.body.locationId!!)
        }

        val resourceRequest = getCarResourcesByCar(request.body.id)

        return if (location != null) {
            CarMapper.buildFromWithLocation(
                response = request.body,
                resources = resourceRequest,
                location = location
            )
        } else {
            CarMapper.buildFrom(
                response = request.body,
                resources = resourceRequest,
            )
        }
    }

    private suspend fun getCarResourcesByCar(carId: Int): List<GetCarResourceResponse> {

        val request = client().getCarResources(carId)

        if (request.failed || !request.isSuccessful) {
            return emptyList()
        }

        return request.body
    }

    suspend fun getCarsByUser(): MutableList<Car> {
        val carList = mutableListOf<Car>()
        val request = client().getCarsByUser()

        if (request.failed || !request.isSuccessful) {
            return carList
        }

        request.body.forEach { item ->

            val resource = getCarResourcesByCar(item.id)
            val car: Car = CarMapper.buildFrom(
                response = item,
                resources = resource
            )
            carList.add(car)
        }
        return carList
    }

    suspend fun createCar(context: Context, car: CarRoom) {
        return try {
            dao(context).createCar(car)
        } catch (e: Exception) {
            Toast.makeText(context, context.getString(R.string.no_database_connection), Toast.LENGTH_LONG).show()
        }
    }
}
