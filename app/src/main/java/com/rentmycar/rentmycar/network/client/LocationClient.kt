package com.rentmycar.rentmycar.network.client

import com.rentmycar.rentmycar.domain.model.Location
import com.rentmycar.rentmycar.network.response.GetLocationByIdResponse
import com.rentmycar.rentmycar.network.response.SimpleResponse
import com.rentmycar.rentmycar.network.service.LocationService

class LocationClient(
    private val locationService: LocationService
): BaseClient() {

    suspend fun getLocationById(id: Int): SimpleResponse<GetLocationByIdResponse> {
        return safeApiCall { locationService.getLocationById(id) }
    }

    suspend fun postLocation(location: Location): SimpleResponse<GetLocationByIdResponse> {
        return safeApiCall { locationService.postLocation(location) }
    }
}