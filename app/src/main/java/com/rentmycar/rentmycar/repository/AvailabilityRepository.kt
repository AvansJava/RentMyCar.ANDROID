package com.rentmycar.rentmycar.repository

import com.rentmycar.rentmycar.domain.mapper.AvailabilityMapper
import com.rentmycar.rentmycar.domain.model.Availability
import com.rentmycar.rentmycar.network.NetworkLayer
import com.rentmycar.rentmycar.network.response.GetTimeslotResponse

class AvailabilityRepository {

    private fun client() = NetworkLayer.availabilityClient

    suspend fun getDefaultTimeslots(): List<GetTimeslotResponse> {
        val request = client().getDefaultTimeslots()

        if (request.failed || !request.isSuccessful) {
            return emptyList()
        }

        return request.body
    }

    suspend fun getCarAvailability(carId: Int): List<Availability> {
        val availabilityList = mutableListOf<Availability>()
        val request = client().getCarAvailability(carId)

        if (request.failed || !request.isSuccessful) {
            return emptyList()
        }

        request.body.forEach { item ->
            val availability: Availability = AvailabilityMapper.buildFrom(
                response = item
            )
            availabilityList.add(availability)
        }
        return availabilityList
    }
}