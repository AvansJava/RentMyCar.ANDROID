package com.rentmycar.rentmycar.controller

import com.airbnb.epoxy.EpoxyController
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.domain.model.LocalException
import com.rentmycar.rentmycar.domain.model.RentalPlan
import com.rentmycar.rentmycar.epoxy.EmptyListEpoxyModel
import com.rentmycar.rentmycar.epoxy.LoadingEpoxyModel

class RentalPlanDetailsEpoxyController: EpoxyController() {
    var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var rentalPlan: RentalPlan? = null
        set(value) {
            field = value
            if (field != null) {
                isLoading = false
                requestModelBuild()
            }
        }

    override fun buildModels() {
        if (isLoading) {
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        if (rentalPlan == null) {
            val localException = LocalException(
                RentMyCarApplication.context.getString(R.string.no_rental_plan_found),
                RentMyCarApplication.context.getString(R.string.no_rental_plan_available)
            )
            EmptyListEpoxyModel(localException).id("emptyList").addTo(this)
            return
        }
    }
}