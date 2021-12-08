package com.rentmycar.rentmycar.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rentmycar.rentmycar.AppPreference
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.controller.UserLoginEpoxyController
import com.rentmycar.rentmycar.domain.model.Login
import com.rentmycar.rentmycar.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_user_login.*

class UserLoginFragment : Fragment() {

    private val viewModel: UserViewModel by lazy {
        ViewModelProvider(this)[UserViewModel::class.java]
    }
    private val epoxyController = UserLoginEpoxyController()
    private val preference = AppPreference(RentMyCarApplication.context)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_user_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userLoginLiveData.observe(viewLifecycleOwner) {userLogin ->
            epoxyController.postLoginResponse = userLogin
            if (userLogin == null) {
                Toast.makeText(requireActivity(), "Unsuccessful network call!", Toast.LENGTH_LONG).show()
                return@observe
            }
            preference.setToken(userLogin.accessToken)
            preference.setUserId(userLogin.userId.toInt())
            preference.setFirstName(userLogin.firstName)
            preference.setLastName(userLogin.lastName)
        }

        tvSignUp.setOnClickListener {
            tvSignUp.setTextColor(Color.parseColor("#FF018786"))
            val directions = UserLoginFragmentDirections.actionUserLoginFragmentToUserRegisterFragment()
            findNavController().navigate(directions)
        }

        btnLogin.setOnClickListener {
            preference.clearPreferences()

            val userEmail: String = email.editText?.text?.trim { it <= ' ' }.toString()
            val userPassword: String = password.editText?.text?.trim { it <= ' ' }.toString()

            if (!validateLoginForm(userEmail, userPassword)) {
                val login = Login(
                    email = userEmail,
                    password = userPassword
                )
                viewModel.userLogin(login)

                val directions = UserLoginFragmentDirections.actionUserLoginFragmentToCarListFragment()
                findNavController().navigate(directions)
            }
        }
    }

    private fun validateLoginForm(userEmail: String, userPassword: String): Boolean {
        var formErrors = false

        if (userEmail.isEmpty()) {
            email.error = "Please fill in your e-mail address."
            formErrors = true
        } else {
            email.error = null
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            email.error = "E-mail address invalid."
            formErrors = true
        } else {
            email.error = null
        }

        if (userPassword.isEmpty()) {
            password.error = "Please fill in your password."
            formErrors = true
        } else {
            password.error = null
        }

        if (userPassword.length < 6) {
            password.error = "Password needs to have a minimum of 6 characters."
            formErrors = true
        } else {
            password.error = null
        }

        return formErrors
    }
}