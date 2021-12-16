package com.rentmycar.rentmycar.repository

import android.util.Log
import android.widget.Toast
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.domain.mapper.RentalPlanMapper
import com.rentmycar.rentmycar.domain.model.RentalPlan
import com.rentmycar.rentmycar.network.NetworkLayer
import com.rentmycar.rentmycar.network.request.PostRentalPlanRequest

class RentalPlanRepository {

    private val carRepository = CarRepository()

    suspend fun getRentalPlanById(id: Int): RentalPlan? {
        val request = NetworkLayer.rentalPlanClient.getRentalPlanById(id)

        if (request.failed || !request.isSuccessful) {
            Toast.makeText(RentMyCarApplication.context,
                RentMyCarApplication.context.getString(R.string.error_get_rental_plan), Toast.LENGTH_LONG).show()
            return null
        }

        val car = carRepository.getCarById(request.body.carId)
        return RentalPlanMapper.buildFrom(response = request.body, car = car)
    }

    suspend fun getRentalPlanByCar(carId: Int): RentalPlan? {
        val request = NetworkLayer.rentalPlanClient.getRentalPlanByCar(carId)

        if (request.failed || !request.isSuccessful) {
            Toast.makeText(RentMyCarApplication.context,
                RentMyCarApplication.context.getString(R.string.error_get_rental_plan), Toast.LENGTH_LONG).show()
            return null
        }
        val car = carRepository.getCarById(request.body.carId)
        return RentalPlanMapper.buildFrom(response = request.body, car = car)
    }

    suspend fun postRentalPlan(rentalPlan: PostRentalPlanRequest): RentalPlan? {
        val request = NetworkLayer.rentalPlanClient.postRentalPlan(rentalPlan)

        if (request.failed || !request.isSuccessful) {
            Toast.makeText(RentMyCarApplication.context,
                RentMyCarApplication.context.getString(R.string.error_post_rental_plan), Toast.LENGTH_LONG).show()
            return null
        }

        return RentalPlanMapper.buildFrom(response = request.body, car = null)
    }

    suspend fun deleteRentalPlan(id: Int): String? {
        val request = NetworkLayer.rentalPlanClient.deleteRentalPlan(id)

        if (request.failed || !request.isSuccessful) {
            Toast.makeText(RentMyCarApplication.context,
                RentMyCarApplication.context.getString(R.string.error_delete_rental_plan), Toast.LENGTH_LONG).show()
            return null
        }

        return request.body
    }

    suspend fun getRentalPlans(): List<RentalPlan?> {
        val rentalPlanList = mutableListOf<RentalPlan>()
        val request = NetworkLayer.rentalPlanClient.getRentalPlans()

        if (request.failed || !request.isSuccessful) {
            Log.d("tag", request.toString())
            return emptyList()
        }

        request.body.forEach { item ->
            val car = carRepository.getCarById(item.carId)

            val rentalPlan: RentalPlan = RentalPlanMapper.buildFrom(
                response = item,
                car = car
            )
            rentalPlanList.add(rentalPlan)
        }
        return rentalPlanList
    }
}