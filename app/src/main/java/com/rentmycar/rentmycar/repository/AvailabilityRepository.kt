package com.rentmycar.rentmycar.repository

import android.util.Log
import android.widget.Toast
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.domain.mapper.AvailabilityMapper
import com.rentmycar.rentmycar.domain.model.Availability
import com.rentmycar.rentmycar.network.NetworkLayer
import com.rentmycar.rentmycar.network.request.PutTimeslotRequest
import com.rentmycar.rentmycar.network.response.GetAvailabilityPageResponse
import com.rentmycar.rentmycar.network.response.GetAvailabilityResponse
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

    suspend fun getCarAvailability(carId: Int, pageSize: Int, pageNumber: Int): GetAvailabilityPageResponse? {
        val request = client().getCarAvailability(carId, pageSize, pageNumber)

        if (request.failed || !request.isSuccessful) {
            Toast.makeText(RentMyCarApplication.context, RentMyCarApplication.context.getString(R.string.error_get_availability_page), Toast.LENGTH_LONG).show()
            return null
        }

        return request.body
    }

    suspend fun updateTimeslotStatus(id: Int, status: PutTimeslotRequest): GetAvailabilityResponse? {
        val request = client().updateTimeslotStatus(id, status)

        if (request.failed || !request.isSuccessful) {
            Toast.makeText(RentMyCarApplication.context, RentMyCarApplication.context.getString(R.string.error_put_timeslot), Toast.LENGTH_LONG).show()
            return null
        }

        return request.body
    }
}