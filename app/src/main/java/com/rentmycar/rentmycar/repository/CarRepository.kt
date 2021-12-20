package com.rentmycar.rentmycar.repository;

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.domain.mapper.CarMapper
import com.rentmycar.rentmycar.domain.mapper.RentalPlanMapper
import com.rentmycar.rentmycar.domain.model.Car
import com.rentmycar.rentmycar.domain.model.Location
import com.rentmycar.rentmycar.domain.model.RentalPlan
import com.rentmycar.rentmycar.network.NetworkLayer;
import com.rentmycar.rentmycar.network.response.GetCarResourceResponse
import com.rentmycar.rentmycar.room.RentMyCarDatabase
import okhttp3.MultipartBody
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

            val rentalPlan = getRentalPlanByCar(item.id)

            val resource = getCarResourcesByCar(item.id)
            val car: Car = CarMapper.buildFrom(
                response = item,
                resources = resource,
                rentalPlan = rentalPlan
            )
            carList.add(car)
        }
        return carList
    }

    suspend fun getCarById(id: Int): Car? {
        val request = client().getCarById(id)
        var location: Location? = null
        if (request.failed || !request.isSuccessful) {
            Toast.makeText(RentMyCarApplication.context, RentMyCarApplication.context.getString(R.string.error_get_car), Toast.LENGTH_LONG).show()
            return null
        }

        if (request.body.locationId != null) {
            location = locationRepository.getLocationById(request.body.locationId!!)
        }

        val resourceRequest = getCarResourcesByCar(request.body.id)
        val rentalPlan = getRentalPlanByCar(request.body.id)

        return if (location != null) {
            CarMapper.buildFromWithLocation(
                response = request.body,
                resources = resourceRequest,
                location = location,
                rentalPlan = rentalPlan
            )
        } else {
            CarMapper.buildFrom(
                response = request.body,
                resources = resourceRequest,
                rentalPlan = rentalPlan
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

            val rentalPlan = getRentalPlanByCar(item.id)

            val resource = getCarResourcesByCar(item.id)
            val car: Car = CarMapper.buildFrom(
                response = item,
                resources = resource,
                rentalPlan = rentalPlan
            )
            carList.add(car)
        }
        return carList
    }

    suspend fun createCar(context: Context, car: CarRoom): Long {
        return try {
            dao(context).createCar(car)
        } catch (e: Exception) {
            Toast.makeText(context, context.getString(R.string.no_database_connection), Toast.LENGTH_LONG).show()
            return 0
        }
    }

    suspend fun updateCar(context: Context, locationId: Int, carId: Int) {
        return try {
            dao(context).updateCar(locationId, carId)
        } catch (e: Exception) {
            Toast.makeText(context, context.getString(R.string.no_database_connection), Toast.LENGTH_LONG).show()
        }
    }

    suspend fun getCar(context: Context, carId: Int): CarRoom? {
        return try {
            dao(context).getCar(carId)
        } catch (e: Exception) {
            Toast.makeText(
                context,
                context.getString(R.string.no_database_connection),
                Toast.LENGTH_LONG
            ).show()
            return null
        }
    }

    suspend fun postCarWithLocation(car: Car): Car? {
        val request = client().postCarWithLocation(car)
        var location: Location? = null
        if (request.failed || !request.isSuccessful) {
            Toast.makeText(RentMyCarApplication.context, RentMyCarApplication.context.getString(R.string.error_post_car), Toast.LENGTH_LONG).show()
            return null
        }

        if (request.body.locationId != null) {
            location = locationRepository.getLocationById(request.body.locationId!!)
        }

        return if (location != null) {
            CarMapper.buildFromWithLocation(
                response = request.body,
                resources = emptyList(),
                location = location,
                rentalPlan = null
            )
        } else {
            CarMapper.buildFrom(
                response = request.body,
                resources = emptyList(),
                rentalPlan = null
            )
        }
    }

    suspend fun putCar(id: Int, car: Car): Car? {
        val request = client().putCar(car, id)
        if (request.failed || !request.isSuccessful) {
            Toast.makeText(RentMyCarApplication.context, RentMyCarApplication.context.getString(R.string.error_put_car), Toast.LENGTH_LONG).show()
            return null
        }

        return CarMapper.buildFrom(
            response = request.body,
            resources = emptyList(),
            rentalPlan = null
        )
    }

    private suspend fun getRentalPlanByCar(carId: Int): RentalPlan? {
        val request = NetworkLayer.rentalPlanClient.getRentalPlanByCar(carId)

        if (request.failed || !request.isSuccessful) {
            return null
        }
        return RentalPlanMapper.buildFrom(response = request.body, car = null)
    }

    suspend fun postCarResource(id: Int, image: MultipartBody.Part): String {
        val request = client().postCarResource(id, image)

        if (request.failed || !request.isSuccessful) {
            return RentMyCarApplication.context.getString(R.string.image_not_uploaded)
        }

        return RentMyCarApplication.context.getString(R.string.image_uploaded)
    }
}

