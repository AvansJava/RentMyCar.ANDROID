package com.rentmycar.rentmycar.epoxy

import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.databinding.ModelHeaderBinding


data class HeaderEpoxyModel(
    val header: String
): ViewBindingKotlinModel<ModelHeaderBinding>(R.layout.model_header) {
    override fun ModelHeaderBinding.bind() {
        nameTextView.text = header
    }

}