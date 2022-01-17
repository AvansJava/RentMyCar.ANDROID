package com.rentmycar.rentmycar.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.domain.model.Register
import com.rentmycar.rentmycar.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_user_register.*
import kotlinx.android.synthetic.main.fragment_user_register.email
import kotlinx.android.synthetic.main.fragment_user_register.password
import kotlinx.android.synthetic.main.fragment_user_register.tvSignIn

class UserRegisterFragment: Fragment() {

    private val viewModel: UserViewModel by lazy {
        ViewModelProvider(this)[UserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(activity!! is AppCompatActivity){
            (requireActivity() as AppCompatActivity).supportActionBar?.show()
        }

        viewModel.userRegisterLiveData.observe(viewLifecycleOwner) { register ->
            btnRegister.isEnabled = true
            btnRegister.text = RentMyCarApplication.context.getString(R.string.create_an_account)
            btnSpinner.visibility = View.GONE

            if (register == null) {
                Toast.makeText(requireActivity(), RentMyCarApplication.context.getString(R.string.network_call_failed), Toast.LENGTH_LONG).show()
                return@observe
            }
        }

        tvSignIn.setOnClickListener {
            tvSignIn.setTextColor(Color.parseColor("#FF018786"))
            val directions =
                UserRegisterFragmentDirections.actionUserRegisterFragmentToUserLoginFragment()
            findNavController().navigate(directions)
        }

        btnRegister.setOnClickListener {
            val userEmail: String = email.editText?.text?.trim { it <= ' ' }.toString()
            val userPassword: String = password.editText?.text?.trim { it <= ' ' }.toString()
            val userFirstName: String = first_name.editText?.text?.trim { it <= ' ' }.toString()
            val userLastName: String = last_name.editText?.text?.trim { it <= ' ' }.toString()

            if (!validateRegisterForm(userEmail, userPassword, userFirstName, userLastName)) {
                val register = Register(
                    email = userEmail,
                    password = userPassword,
                    firstName = userFirstName,
                    lastName = userLastName
                )
                btnRegister.isEnabled = false
                btnRegister.text = RentMyCarApplication.context.getString(R.string.registering)
                btnSpinner.visibility = View.VISIBLE

                viewModel.registerUser(register)
                val directions =
                    UserRegisterFragmentDirections.actionUserRegisterFragmentToUserConfirmationFragment()
                findNavController().navigate(directions)
            }
        }
    }

    private fun validateRegisterForm(
        userEmail: String,
        userPassword: String,
        userFirstName: String,
        userLastName: String
    ): Boolean {
        var formErrors = false

        if (userEmail.isEmpty()) {
            test_email.error = RentMyCarApplication.context.getString(R.string.email_empty)
            formErrors = true
        } else {
            email.error = null
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            test_email.error = RentMyCarApplication.context.getString(R.string.email_incorrect)
            formErrors = true
        } else {
            email.error = null
        }

        if (userPassword.isEmpty()) {
            test_password.error = RentMyCarApplication.context.getString(R.string.password_empty)
            formErrors = true
        } else {
            password.error = null
        }

        if (userPassword.length < 6) {
            test_password.error = RentMyCarApplication.context.getString(R.string.password_incorrect)
            formErrors = true
        } else {
            password.error = null
        }

        if (userFirstName.length < 2) {
            test_first_name.error = RentMyCarApplication.context.getString(R.string.name_incorrect)
            formErrors = true
        } else {
            password.error = null
        }

        if (userLastName.length < 2) {
            test_last_name.error = RentMyCarApplication.context.getString(R.string.name_incorrect)
            formErrors = true
        } else {
            password.error = null
        }

        return formErrors
    }
}