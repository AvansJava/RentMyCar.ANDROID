package com.rentmycar.rentmycar.domain.mapper

import com.rentmycar.rentmycar.domain.model.Availability
import com.rentmycar.rentmycar.network.response.GetAvailabilityResponse

object AvailabilityMapper {

    fun buildFrom(response: GetAvailabilityResponse): Availability {
        return Availability(
            productId = response.productId,
            status = response.status,
            startAt = response.startAt,
            endAt = response.endAt
        )
    }
}