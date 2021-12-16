package com.rentmycar.rentmycar.repository

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
}