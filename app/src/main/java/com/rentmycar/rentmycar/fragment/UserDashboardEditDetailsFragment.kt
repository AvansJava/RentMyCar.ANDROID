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
        street_number_input.setText(safeArgs.address1)
        postal_city_input.setText(safeArgs.address2)
        county_input.setText(safeArgs.address3)
        email_input.setText(safeArgs.email)
        phone_number_input.setText(safeArgs.phoneNumber)

        val user = User(
            city = "test",
            country = "test",
            email = "john.doe@gmail.com",
            firstName = "Test",
            houseNumber = "12",
            iban = "",
            lastName = "Test",
            phoneNumber = "06-123456789",
            postalCode = "5051BN",
            street = "Test"
        )

        btnEditDetails.setOnClickListener {
            viewModel.putUser(user)
            Toast.makeText(RentMyCarApplication.context, RentMyCarApplication.context.getString(R.string.save_successful), Toast.LENGTH_SHORT).show()
        }

    }



}