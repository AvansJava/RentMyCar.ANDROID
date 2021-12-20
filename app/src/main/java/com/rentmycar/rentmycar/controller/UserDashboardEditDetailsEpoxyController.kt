package com.rentmycar.rentmycar.controller

import com.airbnb.epoxy.EpoxyController
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.databinding.FragmentUserDashboardEditDetailsBinding
import com.rentmycar.rentmycar.epoxy.ViewBindingKotlinModel
import kotlinx.android.synthetic.main.fragment_user_dashboard_edit_details.*

class UserDashboardEditDetailsEpoxyController: EpoxyController() {

    override fun buildModels() {

        UserDashboardEditDetails(
            title = "test"
        ).id("header").addTo(this)
    }

    data class UserDashboardEditDetails(
        val title: String
    ) : ViewBindingKotlinModel<FragmentUserDashboardEditDetailsBinding>(R.layout.fragment_user_dashboard_edit_details) {

        override fun FragmentUserDashboardEditDetailsBinding.bind() {

        }
    }
}