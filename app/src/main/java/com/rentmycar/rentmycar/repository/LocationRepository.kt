package com.rentmycar.rentmycar.repository

import android.util.Log
import com.rentmycar.rentmycar.domain.mapper.CarMapper
import com.rentmycar.rentmycar.domain.mapper.LocationMapper
import com.rentmycar.rentmycar.domain.model.Car
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

    suspend fun postLocation(location: Location): Location? {
        val request = NetworkLayer.locationClient.postLocation(location)

        if (request.failed || !request.isSuccessful) {
            return null
        }

        return LocationMapper.buildFrom(response = request.body)
    }

    suspend fun getLocations(): List<Location> {
        val locationList = mutableListOf<Location>()
        val request = NetworkLayer.locationClient.getLocations()

        if (request.failed || !request.isSuccessful) {
            return emptyList()
        }

        request.body.forEach { item ->
            val location: Location = LocationMapper.buildFrom(
                response = item
            )
            locationList.add(location)
        }
        return locationList
    }
}