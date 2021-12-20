package com.rentmycar.rentmycar.controller

import com.airbnb.epoxy.EpoxyController
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.databinding.ModelRentalPlanDataPointsBinding
import com.rentmycar.rentmycar.databinding.ModelRentalPlanHeaderBinding
import com.rentmycar.rentmycar.domain.model.LocalException
import com.rentmycar.rentmycar.domain.model.RentalPlan
import com.rentmycar.rentmycar.epoxy.EmptyListEpoxyModel
import com.rentmycar.rentmycar.epoxy.HeaderEpoxyModel
import com.rentmycar.rentmycar.epoxy.LoadingEpoxyModel
import com.rentmycar.rentmycar.epoxy.ViewBindingKotlinModel
import java.text.SimpleDateFormat
import java.util.*

class RentalPlanListEpoxyController(
    private val onRentalPlanSelected: (Int, Int) -> Unit
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

        HeaderEpoxyModel(RentMyCarApplication.context.getString(R.string.my_rental_plans))
            .id("header").addTo(this)

        if (rentalPlans.isEmpty()) {
            val localException = LocalException(
                RentMyCarApplication.context.getString(R.string.no_rental_plans_found),
                RentMyCarApplication.context.getString(R.string.no_rental_plans_available)
            )
            EmptyListEpoxyModel(localException).id("emptyList").addTo(this)
            return
        }

        rentalPlans.forEach { rentalPlan ->
            if (rentalPlan != null) {
                RentalPlanHeaderItemModel(rentalPlan, onRentalPlanSelected).id("header_${rentalPlan.id}").addTo(this)
                RentalPlanDataItemModel(rentalPlan, onRentalPlanSelected).id("data_${rentalPlan.id}").addTo(this)
            }
        }
    }

    data class RentalPlanHeaderItemModel(
        val rentalPlan: RentalPlan,
        val onRentalPlanSelected: (Int, Int) -> Unit
    ): ViewBindingKotlinModel<ModelRentalPlanHeaderBinding>(R.layout.model_rental_plan_header) {

        override fun ModelRentalPlanHeaderBinding.bind() {

            rentalPlanItalicTextView.text = RentMyCarApplication.context.getString(R.string.car_brand_model,
                rentalPlan.car?.brand, rentalPlan.car?.brandType, rentalPlan.car?.model)

            root.setOnClickListener {
                rentalPlan.id?.let { it1 -> rentalPlan.car?.id?.let { it2 ->
                    onRentalPlanSelected(it1,
                        it2
                    )
                } }
            }
        }
    }

    data class RentalPlanDataItemModel(
        val rentalPlan: RentalPlan,
        val onRentalPlanSelected: (Int, Int) -> Unit
    ): ViewBindingKotlinModel<ModelRentalPlanDataPointsBinding>(R.layout.model_rental_plan_data_points) {

        override fun ModelRentalPlanDataPointsBinding.bind() {

            carTextView.text = RentMyCarApplication.context.getString(R.string.car_brand_model,
                rentalPlan.car?.brand, rentalPlan.car?.brandType, rentalPlan.car?.model)
            availableFromTextView.text = convertDate(rentalPlan.availableFrom)
            availableUntilTextView.text = convertDate(rentalPlan.availableUntil)
            priceTextView.text = RentMyCarApplication.context.getString(R.string.rental_plan_price, rentalPlan.price)

            root.setOnClickListener {
                rentalPlan.id?.let { it1 -> rentalPlan.car?.id?.let { it2 ->
                    onRentalPlanSelected(it1,
                        it2
                    )
                } }
            }
        }


        private fun convertDate(input: String): String {
            val date = SimpleDateFormat("yyyy-MM-dd",
                Locale.getDefault()).parse(input)
            val format = SimpleDateFormat(
                "dd-MM-yyyy",
                Locale.getDefault())
            return format.format(date!!)
        }
    }
}