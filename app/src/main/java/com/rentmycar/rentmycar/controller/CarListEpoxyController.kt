package com.rentmycar.rentmycar.controller

import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.config.Config
import com.rentmycar.rentmycar.databinding.*
import com.rentmycar.rentmycar.domain.model.Car
import com.rentmycar.rentmycar.domain.model.CarResource
import com.rentmycar.rentmycar.domain.model.LocalException
import com.rentmycar.rentmycar.epoxy.EmptyListEpoxyModel
import com.rentmycar.rentmycar.epoxy.HeaderEpoxyModel
import com.rentmycar.rentmycar.epoxy.LoadingEpoxyModel
import com.rentmycar.rentmycar.epoxy.ViewBindingKotlinModel
import com.squareup.picasso.Picasso

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
            EmptyListEpoxyModel(localException).id("emptyList").addTo(this)
            return
        }

        cars.forEach { car ->
            if (car?.rentalPlan != null) {
                CarListItemHeaderModel(car, onCarSelected).id("header_{$car.id}").addTo(this)

                var items = car.resources!!.map { resource ->
                    ImagesCarouselItemEpoxyModel(
                        car.id,
                        resource.filePath,
                        onCarSelected
                    ).id(resource.id)
                }

                if (items.isEmpty()) {
                    items = listOf(
                        ImagesCarouselItemEpoxyModel(
                            car.id,
                            Config.NO_IMAGE_AVAILABLE_URL,
                            onCarSelected
                        ).id("no_image"))
                }

                CarouselModel_()
                    .id("images_carousel_{$car.id}")
                    .padding(
                        Carousel.Padding.dp(8,0,8,0,8)
                    )
                    .models(items)
                    .numViewsToShowOnScreen(1f)
                    .addTo(this)

                CarListItemFooterEpoxyModel(car.id, car.rentalPlan!!.price, onCarSelected).id("footer_${car.id}").addTo(this)
            }
        }
    }

    data class CarListItemHeaderModel(
        val car: Car,
        val onCarSelected: (Int) -> Unit
    ): ViewBindingKotlinModel<ModelCarListItemHeaderBinding>(R.layout.model_car_list_item_header) {

        override fun ModelCarListItemHeaderBinding.bind() {
            titleTextView.text = RentMyCarApplication.context.getString(R.string.car_brand_model, car.brand, car.brandType, car.model)
            when(car.carType) {
                "BEV" -> {
                    carTypeImageView.setImageResource(R.drawable.ic_baseline_electric_car_24)
                }
                "FCEV" -> {
                    carTypeImageView.setImageResource(R.drawable.ic_baseline_fcev)
                }
                "ICE" -> {
                    carTypeImageView.setImageResource(R.drawable.ic_baseline_local_gas_station_24)
                }
            }

            root.setOnClickListener {
                onCarSelected(car.id)
            }
        }
    }

    data class ImagesCarouselItemEpoxyModel(
        val carId: Int,
        val filePath: String,
        val onCarSelected: (Int) -> Unit
    ): ViewBindingKotlinModel<ModelCarImageCarouselItemBinding>(R.layout.model_car_image_carousel_item) {

        override fun ModelCarImageCarouselItemBinding.bind() {
            Picasso.get().load(filePath).into(headerImageView)

            root.setOnClickListener {
                onCarSelected(carId)
            }
        }
    }

    data class CarListItemFooterEpoxyModel(
        val carId: Int,
        val carPrice: Double,
        val onCarSelected: (Int) -> Unit
    ): ViewBindingKotlinModel<ModelCarListItemFooterBinding>(R.layout.model_car_list_item_footer) {
        override fun ModelCarListItemFooterBinding.bind() {
            carPriceTextView.text = RentMyCarApplication.context.getString(R.string.car_price, carPrice)

            root.setOnClickListener {
                onCarSelected(carId)
            }
        }

    }
}