package com.rentmycar.rentmycar.repository

import android.content.Context
import android.widget.Toast
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.domain.mapper.LocationMapper
import com.rentmycar.rentmycar.domain.model.Location
import com.rentmycar.rentmycar.network.NetworkLayer
import com.rentmycar.rentmycar.room.RentMyCarDatabase
import com.rentmycar.rentmycar.room.Location as LocationRoom

class LocationRepository {

    private fun client() = NetworkLayer.locationClient
    private fun dao(context : Context) = RentMyCarDatabase.getInstance(context).locationDao()

    suspend fun getLocationById(id: Int): Location? {
        val request =  client().getLocationById(id)

        if (request.failed || !request.isSuccessful) {
            return null
        }

        return LocationMapper.buildFrom(response = request.body)
    }

    suspend fun postLocation(location: Location): Location? {
        val request =  client().postLocation(location)

        if (request.failed || !request.isSuccessful) {
            return null
        }

        return LocationMapper.buildFrom(response = request.body)
    }

    suspend fun getLocations(): List<Location> {
        val locationList = mutableListOf<Location>()
        val request =  client().getLocations()

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

    suspend fun updateLocation(id: Int, location: Location): Location? {
        val request =  client().updateLocationById(id, location)

        if (request.failed || !request.isSuccessful) {
            return null
        }

        return LocationMapper.buildFrom(response = request.body)
    }

    suspend fun deleteLocation(id: Int): String? {
        val request =  client().deleteLocationById(id)

        if (request.failed || !request.isSuccessful) {
            return null
        }

        return request.body
    }

    suspend fun createLocation(context: Context, location: LocationRoom): Long {
        return try {
            dao(context).createLocation(location)
        } catch (e: Exception) {
            Toast.makeText(context, context.getString(R.string.no_database_connection), Toast.LENGTH_LONG).show()
            return 0
        }
    }
}