package com.rentmycar.rentmycar.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_user_confirmation.*
import kotlinx.android.synthetic.main.fragment_user_confirmation.tvSignIn

class UserConfirmationFragment: Fragment() {

    private val viewModel: UserViewModel by lazy {
        ViewModelProvider(this)[UserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_confirmation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userConfirmationLiveData

        tvSignIn.setOnClickListener {
            tvSignIn.setTextColor(Color.parseColor("#FF018786"))
            val directions =
                UserConfirmationFragmentDirections.actionUserConfirmationFragmentToUserLoginFragment()
            findNavController().navigate(directions)
        }

        btnConfirm.setOnClickListener {
            val confirmationToken: String = confirmation.editText?.text?.trim { it <= ' ' }.toString()

            if (!validateConfirmationToken(confirmationToken)) {
                viewModel.confirmUser(confirmationToken)

                val directions =
                    UserConfirmationFragmentDirections.actionUserConfirmationFragmentToUserLoginFragment()
                findNavController().navigate(directions)
            }
        }
    }

    private fun validateConfirmationToken(confirmationToken: String): Boolean {
        var formErrors = false

        if (confirmationToken.isEmpty()) {
            confirmation.error = "Please fill in your confirmation token."
            formErrors = true
        } else {
            confirmation.error = null
        }

        return formErrors
    }
}