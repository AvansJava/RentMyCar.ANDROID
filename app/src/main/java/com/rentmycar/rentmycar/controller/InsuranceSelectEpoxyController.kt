package com.rentmycar.rentmycar.controller

import com.airbnb.epoxy.EpoxyController
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.databinding.ModelInsuranceSelectItemBinding
import com.rentmycar.rentmycar.domain.model.InsuranceType
import com.rentmycar.rentmycar.domain.model.LocalException
import com.rentmycar.rentmycar.epoxy.EmptyListEpoxyModel
import com.rentmycar.rentmycar.epoxy.HeaderEpoxyModel
import com.rentmycar.rentmycar.epoxy.LoadingEpoxyModel
import com.rentmycar.rentmycar.epoxy.ViewBindingKotlinModel

class InsuranceSelectEpoxyController(
    private val onInsuranceSelected: (String, Double) -> Unit
): EpoxyController() {
    var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var insuranceTypes: List<InsuranceType?> = emptyList()
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

        HeaderEpoxyModel(RentMyCarApplication.context.getString(R.string.select_insurance))
            .id("header").addTo(this)

        if (insuranceTypes.isEmpty()) {
            val localException = LocalException(
                RentMyCarApplication.context.getString(R.string.no_insurance_types_found),
                RentMyCarApplication.context.getString(R.string.no_insurance_types_available)
            )
            EmptyListEpoxyModel(localException).id("emptyList").addTo(this)
            return
        }

        insuranceTypes.forEach { insuranceType ->
            InsuranceListItemEpoxyModel(insuranceType!!, onInsuranceSelected)
                .id("insurance_${insuranceType.insuranceTypeId}").addTo(this)
        }
    }

    data class InsuranceListItemEpoxyModel(
        val insuranceType: InsuranceType,
        val onInsuranceSelected: (String, Double) -> Unit
    ): ViewBindingKotlinModel<ModelInsuranceSelectItemBinding>(R.layout.model_insurance_select_item) {

        override fun ModelInsuranceSelectItemBinding.bind() {

            priceLine.text = RentMyCarApplication.context.getString(R.string.insurance_price, insuranceType.price)

            when(insuranceType.insuranceTypeId) {
                "ALL_RISK" -> {
                    insuranceTypeTextView.text = RentMyCarApplication.context.getString(R.string.title_all_risk)
                    descriptionLine.text = RentMyCarApplication.context.getString(R.string.description_all_risk)
                }
                "ALL_RISK_INTERNATIONAL" -> {
                    insuranceTypeTextView.text = RentMyCarApplication.context.getString(R.string.title_all_risk_international)
                    descriptionLine.text = RentMyCarApplication.context.getString(R.string.description_all_risk_international)
                }
                "BASIC_COVERAGE" -> {
                    insuranceTypeTextView.text = RentMyCarApplication.context.getString(R.string.title_basic_coverage)
                    descriptionLine.text = RentMyCarApplication.context.getString(R.string.description_basic_coverage)
                }
                "BASIC_COVERAGE_INTERNATIONAL" -> {
                    insuranceTypeTextView.text = RentMyCarApplication.context.getString(R.string.title_basic_coverage_international)
                    descriptionLine.text = RentMyCarApplication.context.getString(R.string.description_basic_coverage_international)
                }
            }

            root.setOnClickListener {
                onInsuranceSelected(insuranceType.insuranceTypeId, insuranceType.price)
            }
        }
    }
}