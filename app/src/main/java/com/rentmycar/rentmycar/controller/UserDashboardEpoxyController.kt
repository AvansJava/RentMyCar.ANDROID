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
            title = "This is the user dashboard"
        ).id("header").addTo(this)

//        UserDataEpoxyModel(
//            firstName = getUserResponse!!.firstName,
//            lastName = getUserResponse!!.lastName
//        ).id("name").addTo(this)

    }

    data class HeaderEpoxyModel(
        val title: String
    ) : ViewBindingKotlinModel<ModelUserDashboardHeaderBinding>(R.layout.model_user_dashboard_header) {

        override fun ModelUserDashboardHeaderBinding.bind() {
            userDashboardHeaderWelcome.text = title
        }
    }

//    data class UserDataEpoxyModel(
//        val firstName: String,
//        val lastName: String
//    ): ViewBindingKotlinModel<ModelUserDataBinding>(R.layout.model_user_data) {
//
//        override fun ModelUserDataBinding.bind() {
//            textViewName.text = "$firstName $lastName"
//        }
//    }
}