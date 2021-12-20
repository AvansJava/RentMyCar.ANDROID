package com.rentmycar.rentmycar.network.request

data class PostReservationRequest(
    val rentalPlan: RentalPlanIdRequest,
    val insuranceTypeId: String,
    val insurancePrice: Double,
    val timeslots: List<TimeslotIdRequest>
)
