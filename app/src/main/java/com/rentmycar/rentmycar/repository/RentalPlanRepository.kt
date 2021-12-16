package com.rentmycar.rentmycar.repository

import android.widget.Toast
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.domain.mapper.RentalPlanMapper
import com.rentmycar.rentmycar.domain.model.RentalPlan
import com.rentmycar.rentmycar.network.NetworkLayer

class RentalPlanRepository {

    suspend fun getRentalPlanById(id: Int): RentalPlan? {
        val request = NetworkLayer.rentalPlantClient.getRentalPlanById(id)

        if (request.failed || !request.isSuccessful) {
            Toast.makeText(RentMyCarApplication.context,
                RentMyCarApplication.context.getString(R.string.error_get_rental_plan), Toast.LENGTH_LONG).show()
            return null
        }

        return RentalPlanMapper.buildFrom(response = request.body)
    }

    suspend fun getRentalPlanByCar(carId: Int): RentalPlan? {
        val request = NetworkLayer.rentalPlantClient.getRentalPlanByCar(carId)

        if (request.failed || !request.isSuccessful) {
            Toast.makeText(RentMyCarApplication.context,
                RentMyCarApplication.context.getString(R.string.error_get_rental_plan), Toast.LENGTH_LONG).show()
            return null
        }

        return RentalPlanMapper.buildFrom(response = request.body)
    }

    suspend fun postRentalPlan(rentalPlan: RentalPlan): RentalPlan? {
        val request = NetworkLayer.rentalPlantClient.postRentalPlan(rentalPlan)

        if (request.failed || !request.isSuccessful) {
            Toast.makeText(RentMyCarApplication.context,
                RentMyCarApplication.context.getString(R.string.error_post_rental_plan), Toast.LENGTH_LONG).show()
            return null
        }

        return RentalPlanMapper.buildFrom(response = request.body)
    }

    suspend fun deleteRentalPlan(id: Int): String? {
        val request = NetworkLayer.rentalPlantClient.deleteRentalPlan(id)

        if (request.failed || !request.isSuccessful) {
            Toast.makeText(RentMyCarApplication.context,
                RentMyCarApplication.context.getString(R.string.error_delete_rental_plan), Toast.LENGTH_LONG).show()
            return null
        }

        return request.body
    }

    suspend fun getRentalPlans(): List<RentalPlan?> {
        val rentalPlanList = mutableListOf<RentalPlan>()
        val request = NetworkLayer.rentalPlantClient.getRentalPlans()

        if (request.failed || !request.isSuccessful) {
            return emptyList()
        }

        request.body.forEach { item ->
            val location: RentalPlan = RentalPlanMapper.buildFrom(
                response = item
            )
            rentalPlanList.add(location)
        }
        return rentalPlanList
    }
}