package com.rentmycar.rentmycar.fragment

import android.content.Context
import android.content.SharedPreferences
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
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.config.Config
import com.rentmycar.rentmycar.controller.UserDashboardEpoxyController
import com.rentmycar.rentmycar.controller.UserLoginEpoxyController
import com.rentmycar.rentmycar.domain.model.Login
import com.rentmycar.rentmycar.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_user_login.*
import kotlinx.coroutines.delay

class UserLoginFragment : Fragment() {

    private val viewModel: UserViewModel by lazy {
        ViewModelProvider(this)[UserViewModel::class.java]
    }
    private val sp: SharedPreferences = RentMyCarApplication.context.getSharedPreferences(Config.PREFERENCES, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sp.edit()
    private val epoxyController = UserLoginEpoxyController()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userLoginLiveData.observe(viewLifecycleOwner) {login ->
            epoxyController.postLoginResponse = login
            if (login == null) {
                Toast.makeText(requireActivity(), "Unsuccessful network call!", Toast.LENGTH_LONG).show()
                return@observe
            }
            saveAccessToken(login.accessToken)
        }

        tvSignIn.setOnClickListener {
            tvSignIn.setTextColor(Color.parseColor("#FF018786"))
            val directions = UserLoginFragmentDirections.actionUserLoginFragmentToUserRegisterFragment()
            findNavController().navigate(directions)
        }

        btnLogin.setOnClickListener {
            editor.clear()
            editor.commit()

            val userEmail: String = email.editText?.text?.trim { it <= ' ' }.toString()
            val userPassword: String = password.editText?.text?.trim { it <= ' ' }.toString()

            if (!validateLoginForm(userEmail, userPassword)) {
                val login = Login(
                    email = userEmail,
                    password = userPassword
                )
                viewModel.userLogin(login)
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

    private fun saveAccessToken(token: String) {
        editor.putString(Config.ACCESS_TOKEN, token)
        editor.apply()
    }
}