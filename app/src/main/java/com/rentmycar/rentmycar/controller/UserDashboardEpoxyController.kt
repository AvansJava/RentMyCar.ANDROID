package com.rentmycar.rentmycar.controller

import com.airbnb.epoxy.EpoxyController
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.databinding.ModelUserDashboardHeaderBinding
import com.rentmycar.rentmycar.epoxy.LoadingEpoxyModel
import com.rentmycar.rentmycar.epoxy.ViewBindingKotlinModel
import com.rentmycar.rentmycar.network.response.GetUserResponse

class UserDashboardEpoxyController(
    private val onEditDetailsClicked: (String, String, String, String, String, String, String) -> Unit
): EpoxyController() {

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
            title = RentMyCarApplication.context.getString(R.string.user_dashboard_title, getUserResponse!!.firstName, getUserResponse!!.lastName),
            firstName = getUserResponse!!.firstName,
            lastName = getUserResponse!!.lastName,
            address1 = RentMyCarApplication.context.getString(R.string.user_dashboard_address1, getUserResponse!!.street, getUserResponse!!.houseNumber),
            address2 = RentMyCarApplication.context.getString(R.string.user_dashboard_address2, getUserResponse!!.postalCode, getUserResponse!!.city),
            address3 = RentMyCarApplication.context.getString(R.string.user_dashboard_address3, getUserResponse!!.country),
            phoneNumber = RentMyCarApplication.context.getString(R.string.user_dashboard_phone, getUserResponse!!.phoneNumber),
            emailAddress = RentMyCarApplication.context.getString(R.string.user_dashboard_email, getUserResponse!!.email),
        ).id("header").addTo(this)
    }

    data class HeaderEpoxyModel(
        val title: String,
        val firstName: String,
        val lastName: String,
        val address1: String,
        val address2: String,
        val address3: String,
        val phoneNumber: String,
        val emailAddress: String,
        val onEditDetailsClicked: (String, String, String, String, String, String, String) -> Unit
    ) : ViewBindingKotlinModel<ModelUserDashboardHeaderBinding>(R.layout.model_user_dashboard_header) {

        override fun ModelUserDashboardHeaderBinding.bind() {
            userDashboardHeaderWelcome.text = title
            userDashboardHeaderStreet.text = address1
            userDashboardHeaderCity.text = address2
            userDashboardHeaderCountry.text = address3
            userDashboardHeaderPhone.text = phoneNumber
            userDashboardHeaderEmail.text = emailAddress

            btnUserDashboard.setOnClickListener{
                onEditDetailsClicked(
                    firstName,
                    lastName,
                    address1,
                    address2,
                    address3,
                    phoneNumber,
                    emailAddress
                )
            }

        }
    }
}