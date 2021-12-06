package com.rentmycar.rentmycar.domain.mapper

import com.rentmycar.rentmycar.domain.model.Location
import com.rentmycar.rentmycar.network.response.GetLocationByIdResponse

object LocationMapper {
    fun buildFrom(response: GetLocationByIdResponse): Location {
        return Location(
            id = response.id,
            street = response.street,
            houseNumber = response.houseNumber,
            postalCode = response.postalCode,
            city = response.city,
            country = response.country,
            latitude = response.latitude,
            longitude = response.longitude
        )
    }
}