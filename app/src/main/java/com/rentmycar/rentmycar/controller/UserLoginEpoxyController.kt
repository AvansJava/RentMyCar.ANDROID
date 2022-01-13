package com.rentmycar.rentmycar.controller

import android.widget.Toast
import com.airbnb.epoxy.EpoxyController
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.epoxy.LoadingEpoxyModel
import com.rentmycar.rentmycar.network.response.PostLoginResponse

class UserLoginEpoxyController: EpoxyController() {
    var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var postLoginResponse: PostLoginResponse? = null
        set(value) {
            field = value
            if (field != null) {
                isLoading = false
                requestModelBuild()
            }
        }

    override fun buildModels() {

        // Show loading spinner when request is still processing
        if (isLoading) {
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        if (postLoginResponse == null) {
            Toast.makeText(RentMyCarApplication.context, RentMyCarApplication.context.getString(R.string.network_call_failed), Toast.LENGTH_LONG).show()
            return
        }
    }
}