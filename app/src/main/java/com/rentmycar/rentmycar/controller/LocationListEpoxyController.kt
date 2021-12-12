package com.rentmycar.rentmycar.controller

import com.airbnb.epoxy.EpoxyController
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.databinding.ModelHeaderBinding
import com.rentmycar.rentmycar.databinding.ModelLocalExceptionErrorStateBinding
import com.rentmycar.rentmycar.databinding.ModelLocationListHeaderBinding
import com.rentmycar.rentmycar.databinding.ModelLocationListItemBinding
import com.rentmycar.rentmycar.domain.model.LocalException
import com.rentmycar.rentmycar.domain.model.Location
import com.rentmycar.rentmycar.epoxy.EmptyListEpoxyModel
import com.rentmycar.rentmycar.epoxy.LoadingEpoxyModel
import com.rentmycar.rentmycar.epoxy.ViewBindingKotlinModel

class LocationListEpoxyController(
    private val onLocationSelected: (Int) -> Unit
): EpoxyController() {
    var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var locations: List<Location?> = emptyList()
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

        HeaderEpoxyModel(RentMyCarApplication.context.getString(R.string.my_locations))
            .id("header").addTo(this)

        if (locations.isEmpty()) {
            val localException = LocalException(
                RentMyCarApplication.context.getString(R.string.no_locations_found),
                RentMyCarApplication.context.getString(R.string.no_locations_available)
            )
            EmptyListEpoxyModel(localException).id("emptyList").addTo(this)
            return
        }

        locations.forEach { location ->
            if (location != null) {
                LocationListItemModel(location, onLocationSelected).id("header_{location.id}").addTo(this)
            }
        }
    }

    data class HeaderEpoxyModel(
        val header: String
    ): ViewBindingKotlinModel<ModelHeaderBinding>(R.layout.model_header) {
        override fun ModelHeaderBinding.bind() {
            nameTextView.text = header
        }

    }

    data class LocationListItemModel(
        val location: Location,
        val onLocationSelected: (Int) -> Unit
    ): ViewBindingKotlinModel<ModelLocationListItemBinding>(R.layout.model_location_list_item) {

        override fun ModelLocationListItemBinding.bind() {
            locationTextView.text = RentMyCarApplication.context.getString(R.string.location_list_item,
            location.street, location.houseNumber, location.postalCode, location.city, location.country)

            root.setOnClickListener {
                location.id?.let { it1 -> onLocationSelected(it1) }
            }
        }
    }
}