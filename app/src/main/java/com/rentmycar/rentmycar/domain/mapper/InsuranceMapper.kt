package com.rentmycar.rentmycar.domain.mapper

import com.rentmycar.rentmycar.domain.model.InsuranceType
import com.rentmycar.rentmycar.network.response.GetInsuranceTypesResponse

object InsuranceMapper {
    fun buildFrom(response: GetInsuranceTypesResponse): InsuranceType {
        return InsuranceType(
            insuranceTypeId = response.insuranceTypeId,
            price = response.price
        )
    }
}