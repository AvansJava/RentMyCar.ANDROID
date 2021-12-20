package com.rentmycar.rentmycar.domain.mapper

import com.rentmycar.rentmycar.domain.model.Product
import com.rentmycar.rentmycar.network.response.GetReservationResponse

object ProductMapper {

    fun buildFrom(response: GetReservationResponse): Product {
        return Product(
            id = response.product.id,
            reservationNumber = response.product.reservationNumber,
            price = response.product.price,
            rentalPlanId = response.product.rentalPlanId,
            insuranceTypeId = response.product.insuranceTypeId,
            insurancePrice = response.product.insurancePrice,
            status = response.product.status,
            createdAt = response.product.createdAt,
            updatedAt = response.product.updatedAt
        )
    }
}