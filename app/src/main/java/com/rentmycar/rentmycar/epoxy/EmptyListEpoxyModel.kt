package com.rentmycar.rentmycar.epoxy

import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.databinding.ModelLocalExceptionErrorStateBinding
import com.rentmycar.rentmycar.domain.model.LocalException

class EmptyListEpoxyModel(
    private val localException: LocalException
): ViewBindingKotlinModel<ModelLocalExceptionErrorStateBinding>(R.layout.model_local_exception_error_state) {

    // Shows a custom title/description (error or not found for example) when API doesn't return results
    override fun ModelLocalExceptionErrorStateBinding.bind() {
        titleTextView.text = localException.title
        descriptionTextView.text = localException.description
    }
}