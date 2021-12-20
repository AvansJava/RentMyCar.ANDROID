package com.rentmycar.rentmycar.repository

import com.rentmycar.rentmycar.domain.mapper.InsuranceMapper
import com.rentmycar.rentmycar.domain.model.InsuranceType
import com.rentmycar.rentmycar.network.NetworkLayer

class InsuranceRepository {

    private fun client() = NetworkLayer.insuranceClient

    suspend fun getInsuranceTypes(): List<InsuranceType> {
        val insuranceTypeList = mutableListOf<InsuranceType>()
        val request =  client().getInsuranceTypes()

        if (request.failed || !request.isSuccessful) {
            return emptyList()
        }

        request.body.forEach { item ->
            val insuranceType: InsuranceType = InsuranceMapper.buildFrom(
                response = item
            )
            insuranceTypeList.add(insuranceType)
        }
        return insuranceTypeList
    }
}