package com.rentmycar.rentmycar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.domain.model.Login
import com.rentmycar.rentmycar.domain.model.User
import com.rentmycar.rentmycar.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_user_dashboard_edit_details.*

class UserDashboardEditDetailsFragment: Fragment() {

    private val viewModel: UserViewModel by lazy {
        ViewModelProvider(this)[UserViewModel::class.java]
    }

    private val safeArgs: UserDashboardEditDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_dashboard_edit_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        first_name_input.setText(safeArgs.firstName)
        last_name_input.setText(safeArgs.lastName)
        street_name_input.setText(safeArgs.streetName)
        house_number_input.setText(safeArgs.houseNumber)
        city_input.setText(safeArgs.city)
        postal_code_input.setText(safeArgs.postalCode)
        county_input.setText(safeArgs.country)
        email_input.setText(safeArgs.email)
        phone_number_input.setText(safeArgs.phoneNumber)

        btnEditDetails.setOnClickListener {
            val user = User(
                city = city_input.text.toString(),
                country = county_input.text.toString(),
                email = "john.doe@gmail.com",
                firstName = first_name_input.text.toString(),
                houseNumber = house_number_input.text.toString(),
                iban = "",
                lastName = last_name_input.text.toString(),
                phoneNumber = phone_number_input.text.toString(),
                postalCode = postal_code_input.text.toString(),
                street = street_name_input.text.toString()
            )

            viewModel.putUser(user)
//            Toast.makeText(RentMyCarApplication.context, user.postalCode, Toast.LENGTH_SHORT).show()
            Toast.makeText(RentMyCarApplication.context, RentMyCarApplication.context.getString(R.string.save_successful), Toast.LENGTH_SHORT).show()
        }

    }



}