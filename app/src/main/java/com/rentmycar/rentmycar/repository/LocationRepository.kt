package com.rentmycar.rentmycar.repository

import com.rentmycar.rentmycar.domain.mapper.LocationMapper
import com.rentmycar.rentmycar.domain.model.Location
import com.rentmycar.rentmycar.network.NetworkLayer

class LocationRepository {

    suspend fun getLocationById(id: Int): Location? {
        val request = NetworkLayer.locationClient.getLocationById(id)

        if (request.failed || !request.isSuccessful) {
            return null
        }

        return LocationMapper.buildFrom(response = request.body)
    }
}