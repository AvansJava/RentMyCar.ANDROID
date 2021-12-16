package com.rentmycar.rentmycar.controller

import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.databinding.*
import com.rentmycar.rentmycar.domain.model.Car
import com.rentmycar.rentmycar.epoxy.HeaderEpoxyModel
import com.rentmycar.rentmycar.epoxy.LoadingEpoxyModel
import com.rentmycar.rentmycar.epoxy.ViewBindingKotlinModel
import com.rentmycar.rentmycar.room.Car as CarRoom
import com.rentmycar.rentmycar.room.Location

class CarCreateOverviewEpoxyController(
    private val onContinueSelected: (Int) -> Unit
): EpoxyController() {
    private var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var car: CarRoom? = null
        set(value) {
            field = value
            if (field != null) {
                isLoading = false
                requestModelBuild()
            }
        }

    var createdCar: Car? = null
        set(value) {
            field = value
            if (field != null) {
                isLoading = false
                requestModelBuild()
            }
        }

    var location: Location? = null
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

        HeaderEpoxyModel(RentMyCarApplication.context.getString(R.string.car_create_overview))
            .id("header").addTo(this)

        CarDetailsTitleEpoxyModel(true).id("title").addTo(this)

        DataPointEpoxyModel(
            title = RentMyCarApplication.context.getString(R.string.brand),
            description = car!!.brand
        ).id("data_point_1").addTo(this)

        DataPointEpoxyModel(
            title = RentMyCarApplication.context.getString(R.string.brand_type),
            description = car!!.brandType
        ).id("data_point_2").addTo(this)

        DataPointEpoxyModel(
            title = RentMyCarApplication.context.getString(R.string.model),
            description = car!!.model
        ).id("data_point_3").addTo(this)

        DataPointEpoxyModel(
            title = RentMyCarApplication.context.getString(R.string.car_license_plate),
            description = car!!.licensePlateNumber
        ).id("data_point_4").addTo(this)

        DataPointConsumptionEpoxyModel(
            title = RentMyCarApplication.context.getString(R.string.consumption),
            description = car!!.consumption,
            carType = car!!.carType
        ).id("data_point_5").addTo(this)

        DataPointEpoxyModel(
            title = RentMyCarApplication.context.getString(R.string.car_cost_price),
            description = car!!.costPrice.toString(),
        ).id("data_point_6").addTo(this)

        DataPointEpoxyModel(
            title = RentMyCarApplication.context.getString(R.string.car_type),
            description = car!!.carType
        ).id("data_point_7").addTo(this)

        if (location != null) {
            LocationEpoxyModel(
                location = location,
                true
            ).id("location").addTo(this)
        }

        if (createdCar != null) {
            ButtonsEpoxyModel(createdCar!!.id, onContinueSelected).id("buttons").addTo(this)
        }
    }

    class CarDetailsTitleEpoxyModel(
        private val hideEditButton: Boolean
    ): ViewBindingKotlinModel<ModelCarDetailsTitleBinding>(R.layout.model_car_details_title) {

        override fun ModelCarDetailsTitleBinding.bind() {
            if (hideEditButton) {
                carEditImageView.visibility = View.GONE
            }
        }
    }

    data class DataPointConsumptionEpoxyModel(
        val title: String,
        val description: Double,
        val carType: String?
    ) : ViewBindingKotlinModel<ModelCarDetailsDataPointBinding>(R.layout.model_car_details_data_point) {

        override fun ModelCarDetailsDataPointBinding.bind() {
            labelTextView.text = title
            textView.text = description.toString()
        }
    }

    data class DataPointEpoxyModel(
        val title: String,
        val description: String
    ) : ViewBindingKotlinModel<ModelCarDetailsDataPointBinding>(R.layout.model_car_details_data_point) {

        override fun ModelCarDetailsDataPointBinding.bind() {
            labelTextView.text = title
            textView.text = description
        }

    }

    data class LocationEpoxyModel(
        val location: Location?,
        private val hideEditButton: Boolean
    ): ViewBindingKotlinModel<ModelCarDetailsLocationDataPointBinding>(R.layout.model_car_details_location_data_point) {

        override fun ModelCarDetailsLocationDataPointBinding.bind() {
            addressLine.text = RentMyCarApplication.context.getString(R.string.address_line, location?.street, location?.houseNumber)
            postalCodeLine.text = RentMyCarApplication.context.getString(R.string.location_line, location?.postalCode)
            cityLine.text = RentMyCarApplication.context.getString(R.string.location_line, location?.city)
            countryLine.text = RentMyCarApplication.context.getString(R.string.location_line, location?.country)

            if (hideEditButton) {
                locationEditImageView.visibility = View.GONE
            }
        }
    }

    data class ButtonsEpoxyModel(
        val carId: Int,
        private val onContinueSelected: (Int) -> Unit
    ): ViewBindingKotlinModel<ModelCarCreateOverviewButtonsBinding>(R.layout.model_car_create_overview_buttons) {
        override fun ModelCarCreateOverviewButtonsBinding.bind() {
            btnContinue.setOnClickListener {
                onContinueSelected(carId)
            }
        }
    }
}