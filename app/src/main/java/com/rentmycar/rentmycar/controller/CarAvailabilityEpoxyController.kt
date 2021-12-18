package com.rentmycar.rentmycar.controller

import com.airbnb.epoxy.EpoxyController
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.databinding.ModelCarListItemHeaderBinding
import com.rentmycar.rentmycar.epoxy.LoadingEpoxyModel
import com.rentmycar.rentmycar.epoxy.ViewBindingKotlinModel

class CarAvailabilityEpoxyController: EpoxyController() {
    private var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var carName: String? = null
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
    }

    data class CarListItemHeaderModel(
        val carName: String
    ): ViewBindingKotlinModel<ModelCarListItemHeaderBinding>(R.layout.model_car_list_item_header) {

        override fun ModelCarListItemHeaderBinding.bind() {
            titleTextView.text = carName
            carTypeImageView.setImageResource(R.drawable.ic_baseline_directions_car_24)
        }
    }
}