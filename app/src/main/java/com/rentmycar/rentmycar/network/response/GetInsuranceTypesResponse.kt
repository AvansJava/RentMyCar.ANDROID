package com.rentmycar.rentmycar.network.response

data class GetInsuranceTypesResponse(
    val insuranceTypeId: String,
    val nameTranslationTag: String,
    val descriptionTranslationTag: String,
    val price: Double
)
