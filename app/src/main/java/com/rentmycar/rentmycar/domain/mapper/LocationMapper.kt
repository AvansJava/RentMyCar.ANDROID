package com.rentmycar.rentmycar.domain.mapper

import com.rentmycar.rentmycar.domain.model.Location
import com.rentmycar.rentmycar.network.response.GetLocationResponse

object LocationMapper {
    fun buildFrom(response: GetLocationResponse): Location {
        return Location(
            street = response.street,
            houseNumber = response.houseNumber,
            postalCode = response.postalCode,
            city = response.city,
            country = response.country,
            latitude = response.latitude,
            longitude = response.longitude,
            id = response.id,
            userId = response.userId
        )
    }
}