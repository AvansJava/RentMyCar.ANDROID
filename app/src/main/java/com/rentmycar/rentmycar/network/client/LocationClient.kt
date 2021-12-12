package com.rentmycar.rentmycar.network.client

import com.rentmycar.rentmycar.domain.model.Location
import com.rentmycar.rentmycar.network.response.GetLocationResponse
import com.rentmycar.rentmycar.network.response.SimpleResponse
import com.rentmycar.rentmycar.network.service.LocationService

class LocationClient(
    private val locationService: LocationService
): BaseClient() {

    suspend fun getLocationById(id: Int): SimpleResponse<GetLocationResponse> {
        return safeApiCall { locationService.getLocationById(id) }
    }

    suspend fun postLocation(location: Location): SimpleResponse<GetLocationResponse> {
        return safeApiCall { locationService.postLocation(location) }
    }

    suspend fun getLocations(): SimpleResponse<List<GetLocationResponse>> {
        return safeApiCall { locationService.getLocations() }
    }

    suspend fun updateLocationById(id: Int, location: Location): SimpleResponse<GetLocationResponse> {
        return safeApiCall { locationService.updateLocationById(id, location) }
    }

    suspend fun deleteLocationById(id: Int): SimpleResponse<String> {
        return safeApiCall { locationService.deleteLocationById(id) }
    }
}