package com.rentmycar.rentmycar.controller

import android.graphics.Color
import android.view.View
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController
import com.rentmycar.rentmycar.AppPreference
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.databinding.*
import com.rentmycar.rentmycar.domain.model.Car
import com.rentmycar.rentmycar.domain.model.CarResource
import com.rentmycar.rentmycar.domain.model.Location
import com.rentmycar.rentmycar.epoxy.LoadingEpoxyModel
import com.rentmycar.rentmycar.epoxy.ViewBindingKotlinModel
import com.squareup.picasso.Picasso

class CarDetailsEpoxyController(
    private val onLocationBtnClicked: (Int) -> Unit,
    private val onEditLocationBtnClicked: (Int) -> Unit,
    private val onEditCarBtnClicked: (Int) -> Unit
): EpoxyController() {

    private val preference = AppPreference(RentMyCarApplication.context)
    private var hideEditButtons: Boolean = false

    var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var car: Car? = null
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

        if (car == null) {
            // todo error state
            return
        }

        if(preference.getUserId() != car!!.userId) {
            hideEditButtons = true
        }

        HeaderEpoxyModel(
            brand = car!!.brand,
            brandType = car!!.brandType,
            model = car!!.model,
            carType = car!!.carType
        ).id("header").addTo(this)

        if (car!!.resources!!.isNotEmpty()) {
            val items = car!!.resources!!.map {
                ImagesCarouselItemEpoxyModel(it).id(it.id)
            }

            CarouselModel_()
                .id("images_carousel")
                .padding(
                    Carousel.Padding.dp(8,0,8,0,8)
                )
                .models(items)
                .numViewsToShowOnScreen(1f)
                .addTo(this)
        }

        TitleEpoxyModel(car, hideEditButtons, onEditCarBtnClicked).id("title").addTo(this)

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

        DataPointConsumptionEpoxyModel(
            title = RentMyCarApplication.context.getString(R.string.consumption),
            description = car!!.consumption,
            carType = car!!.carType
        ).id("data_point_4").addTo(this)

        if (car!!.location != null) {
            LocationEpoxyModel(
                location = car!!.location,
                hideEditButtons,
                onEditLocationBtnClicked
            ).id("location").addTo(this)

            MapEpoxyModel(onLocationBtnClicked, locationId = car!!.location?.id).id("map").addTo(this)
        }
    }

    data class HeaderEpoxyModel(
        val brand: String,
        val brandType: String,
        val model: String,
        val carType: String
    ) : ViewBindingKotlinModel<ModelCarDetailsHeaderBinding>(R.layout.model_car_details_header) {

        override fun ModelCarDetailsHeaderBinding.bind() {
            carTextView.text = RentMyCarApplication.context.getString(R.string.car_brand_model, brand, brandType, model)

            when(carType) {
                "BEV" -> {
                    carTypeTextView.text = RentMyCarApplication.context.getString(R.string.car_type_bev)
                    carTypeImageView.setImageResource(R.drawable.ic_baseline_electric_car_24)
                }
                "FCEV" -> {
                    carTypeTextView.text = RentMyCarApplication.context.getString(R.string.car_type_fcev)
                    carTypeImageView.setImageResource(R.drawable.ic_baseline_fcev)
                }
                "ICE" -> {
                    carTypeTextView.text = RentMyCarApplication.context.getString(R.string.car_type_ice)
                    carTypeImageView.setImageResource(R.drawable.ic_baseline_local_gas_station_24)
                }
            }
        }
    }

    data class ImagesCarouselItemEpoxyModel(
        val carResource: CarResource
    ): ViewBindingKotlinModel<ModelCarImageCarouselItemBinding>(R.layout.model_car_image_carousel_item) {

        override fun ModelCarImageCarouselItemBinding.bind() {
            Picasso.get().load(carResource.filePath).into(headerImageView)
        }
    }

    data class DataPointConsumptionEpoxyModel(
        val title: String,
        val description: Double,
        val carType: String?
    ) : ViewBindingKotlinModel<ModelCarDetailsDataPointBinding>(R.layout.model_car_details_data_point) {

        override fun ModelCarDetailsDataPointBinding.bind() {
            labelTextView.text = title

            when(carType) {
                "BEV" -> {
                    textView.text = RentMyCarApplication.context.getString(R.string.consumption_bev, description)
                }
                "FCEV" -> {
                    textView.text = RentMyCarApplication.context.getString(R.string.consumption_fcev, description)
                }
                "ICE" -> {
                    textView.text = RentMyCarApplication.context.getString(R.string.consumption_ice, description)
                }
            }
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

    data class MapEpoxyModel(
        val onLocationBtnClicked: (Int) -> Unit,
        val locationId: Int?
    ): ViewBindingKotlinModel<ModelCarDetailsMapBinding>(R.layout.model_car_details_map) {
        override fun ModelCarDetailsMapBinding.bind() {
            btnViewOnMap.setOnClickListener {
                if (locationId != null) {
                    onLocationBtnClicked(locationId)
                }
            }
        }

    }

    data class LocationEpoxyModel(
        val location: Location?,
        private val hideEditButton: Boolean,
        val onEditLocationBtnClicked: (Int) -> Unit
    ): ViewBindingKotlinModel<ModelCarDetailsLocationDataPointBinding>(R.layout.model_car_details_location_data_point) {

        override fun ModelCarDetailsLocationDataPointBinding.bind() {
            addressLine.text = RentMyCarApplication.context.getString(R.string.address_line, location?.street, location?.houseNumber)
            postalCodeLine.text = RentMyCarApplication.context.getString(R.string.location_line, location?.postalCode)
            cityLine.text = RentMyCarApplication.context.getString(R.string.location_line, location?.city)
            countryLine.text = RentMyCarApplication.context.getString(R.string.location_line, location?.country)

            if (hideEditButton) {
                locationEditImageView.visibility = View.GONE
            }

            locationEditImageView.setOnClickListener {
                if (location?.id != null) {
                    locationEditImageView.setBackgroundColor(Color.parseColor("#BABABA"))
                    onEditLocationBtnClicked(location.id)
                }
            }
        }
    }

    class TitleEpoxyModel(
        val car: Car?,
        private val hideEditButton: Boolean,
        val onEditCarBtnClicked: (Int) -> Unit
    ): ViewBindingKotlinModel<ModelCarDetailsTitleBinding>(R.layout.model_car_details_title) {

        override fun ModelCarDetailsTitleBinding.bind() {
            if (hideEditButton) {
                carEditImageView.visibility = View.GONE
            }

            carEditImageView.setOnClickListener {
                if (car?.id != null) {
                    carEditImageView.setBackgroundColor(Color.parseColor("#BABABA"))
                    onEditCarBtnClicked(car.id)
                }
            }
        }
    }
}