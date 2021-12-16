package com.rentmycar.rentmycar.controller

import com.airbnb.epoxy.EpoxyController
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.domain.model.LocalException
import com.rentmycar.rentmycar.domain.model.RentalPlan
import com.rentmycar.rentmycar.epoxy.EmptyListEpoxyModel
import com.rentmycar.rentmycar.epoxy.LoadingEpoxyModel

class RentalPlanListEpoxyController(
    private val onRentalPlanSelected: (Int) -> Unit
): EpoxyController() {
    var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var rentalPlans: List<RentalPlan?> = emptyList()
        set(value) {
            field = value
            isLoading = false
            requestModelBuild()
        }

    override fun buildModels() {
        if (isLoading) {
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        LocationListEpoxyController.HeaderEpoxyModel(RentMyCarApplication.context.getString(R.string.my_locations))
            .id("header").addTo(this)

        if (locations.isEmpty()) {
            val localException = LocalException(
                RentMyCarApplication.context.getString(R.string.no_locations_found),
                RentMyCarApplication.context.getString(R.string.no_locations_available)
            )
            EmptyListEpoxyModel(localException).id("emptyList").addTo(this)
            return
        }
    }
}