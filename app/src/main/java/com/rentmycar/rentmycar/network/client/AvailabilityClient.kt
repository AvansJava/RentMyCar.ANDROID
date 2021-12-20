package com.rentmycar.rentmycar.network.client

import com.rentmycar.rentmycar.network.request.PutTimeslotRequest
import com.rentmycar.rentmycar.network.response.GetAvailabilityPageResponse
import com.rentmycar.rentmycar.network.response.GetAvailabilityResponse
import com.rentmycar.rentmycar.network.response.GetTimeslotResponse
import com.rentmycar.rentmycar.network.response.SimpleResponse
import com.rentmycar.rentmycar.network.service.AvailabilityService

class AvailabilityClient(
    private val availabilityService: AvailabilityService
): BaseClient() {

    suspend fun getDefaultTimeslots(): SimpleResponse<List<GetTimeslotResponse>> {
        return safeApiCall { availabilityService.getDefaultTimeslots() }
    }

    suspend fun getCarAvailability(carId: Int, pageSize: Int, pageNumber: Int): SimpleResponse<GetAvailabilityPageResponse> {
        return safeApiCall { availabilityService.getCarAvailability(carId, pageSize, pageNumber) }
    }

    suspend fun updateTimeslotStatus(id: Int, status: PutTimeslotRequest): SimpleResponse<GetAvailabilityResponse> {
        return safeApiCall { availabilityService.updateTimeslotStatus(id, status) }
    }
}