package com.rentmycar.rentmycar.controller

import com.airbnb.epoxy.EpoxyController
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.databinding.ModelCarListHeaderBinding
import com.rentmycar.rentmycar.databinding.ModelLocalExceptionErrorStateBinding
import com.rentmycar.rentmycar.databinding.ViewHolderCarBinding
import com.rentmycar.rentmycar.domain.model.Car
import com.rentmycar.rentmycar.domain.model.LocalException
import com.rentmycar.rentmycar.epoxy.LoadingEpoxyModel
import com.rentmycar.rentmycar.epoxy.ViewBindingKotlinModel

class CarListEpoxyController(
    private val onCarSelected: (Int) -> Unit
): EpoxyController() {
    var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var cars: List<Car?> = emptyList()
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

        HeaderEpoxyModel(RentMyCarApplication.context.getString(R.string.choose_a_car))
            .id("header").addTo(this)

        if (cars.isEmpty()) {
            val localException = LocalException(
                RentMyCarApplication.context.getString(R.string.no_cars_found),
                RentMyCarApplication.context.getString(R.string.no_cars_available)
            )
            EmptyCarListEpoxyModel(localException).id("emptyList").addTo(this)
            return
        }

        cars.forEach { car ->
            if (car != null) {
                CarEpoxyModel(car, onCarSelected).id(car.id).addTo(this)
            }
        }
    }

    data class HeaderEpoxyModel(
        val header: String
    ): ViewBindingKotlinModel<ModelCarListHeaderBinding>(R.layout.model_car_list_header) {
        override fun ModelCarListHeaderBinding.bind() {
            nameTextView.text = header
        }

    }

    data class CarEpoxyModel(
        val car: Car,
        val onCarSelected: (Int) -> Unit
    ) : ViewBindingKotlinModel<ViewHolderCarBinding>(R.layout.view_holder_car) {
        override fun ViewHolderCarBinding.bind() {
            titleTextView.text = "${car.brand} ${car.brandType} ${car.model}"
            carPriceTextView.text = "Starting at 80,- per hour"

            root.setOnClickListener {
                onCarSelected(car.id)
            }
        }

    }

    data class EmptyCarListEpoxyModel(
        val localException: LocalException
    ): ViewBindingKotlinModel<ModelLocalExceptionErrorStateBinding>(R.layout.model_local_exception_error_state) {
        override fun ModelLocalExceptionErrorStateBinding.bind() {
            titleTextView.text = localException.title
            descriptionTextView.text = localException.description
        }

    }

}