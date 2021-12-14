package com.rentmycar.rentmycar.controller

import com.airbnb.epoxy.EpoxyController
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.databinding.ModelUserDashboardHeaderBinding
import com.rentmycar.rentmycar.epoxy.LoadingEpoxyModel
import com.rentmycar.rentmycar.epoxy.ViewBindingKotlinModel
import com.rentmycar.rentmycar.network.response.GetUserResponse

class UserDashboardEpoxyController: EpoxyController() {

    var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var getUserResponse: GetUserResponse? = null
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

        if (getUserResponse == null) {
            // todo error state
            return
        }

        HeaderEpoxyModel(
            title = "Hi, ${getUserResponse!!.firstName} ${getUserResponse!!.lastName}.",
            address1 = "${getUserResponse!!.street} ${getUserResponse!!.houseNumber}",
            address2 = "${getUserResponse!!.postalCode} ${getUserResponse!!.city}",
            address3 = "${getUserResponse!!.country}",
            phoneNumber = "${getUserResponse!!.phoneNumber}",
            emailAddress = getUserResponse!!.email,
        ).id("header").addTo(this)
    }

    data class HeaderEpoxyModel(
        val title: String,
        val address1: String,
        val address2: String,
        val address3: String,
        val phoneNumber: String,
        val emailAddress: String,
    ) : ViewBindingKotlinModel<ModelUserDashboardHeaderBinding>(R.layout.model_user_dashboard_header) {

        override fun ModelUserDashboardHeaderBinding.bind() {
            userDashboardHeaderWelcome.text = title
            userDashboardHeaderStreet.text = address1
            userDashboardHeaderCity.text = address2
            userDashboardHeaderCountry.text = address3
            userDashboardHeaderPhone.text = phoneNumber
            userDashboardHeaderEmail.text = emailAddress
        }
    }
}