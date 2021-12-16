package com.rentmycar.rentmycar.network.client

import com.rentmycar.rentmycar.network.response.GetTimeslotResponse
import com.rentmycar.rentmycar.network.response.SimpleResponse
import com.rentmycar.rentmycar.network.service.AvailabilityService

class AvailabilityClient(
    private val availabilityService: AvailabilityService
): BaseClient() {

    suspend fun getDefaultTimeslots(): SimpleResponse<List<GetTimeslotResponse>> {
        return safeApiCall { availabilityService.getDefaultTimeslots() }
    }
}