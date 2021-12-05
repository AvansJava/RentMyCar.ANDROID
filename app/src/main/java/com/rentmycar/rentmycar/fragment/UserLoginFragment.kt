package com.rentmycar.rentmycar.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rentmycar.rentmycar.R
import kotlinx.android.synthetic.main.fragment_user_login.*

class UserLoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvSignIn.setOnClickListener {
            tvSignIn.setTextColor(Color.parseColor("#FF018786"))
            val directions = UserLoginFragmentDirections.actionUserLoginFragmentToUserRegisterFragment()
            findNavController().navigate(directions)
        }

        btnLogin.setOnClickListener {
            val userEmail: String = email.editText?.text?.trim { it <= ' ' }.toString()
            val userPassword: String = password.editText?.text?.trim { it <= ' ' }.toString()

            if (!validateLoginForm(userEmail, userPassword)) {
                userLogin(userEmail, userPassword)
            }
        }
    }

    private fun userLogin(email: String, password: String) {
        val directions = UserLoginFragmentDirections.actionUserLoginFragmentToUserDashboardFragment(
            email = email,
            password = password
        )
        findNavController().navigate(directions)
    }

    private fun validateLoginForm(userEmail: String, userPassword: String): Boolean {
        var formErrors: Boolean = false

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