package com.rentmycar.rentmycar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rentmycar.rentmycar.R
import kotlinx.android.synthetic.main.fragment_user_dashboard_edit_details.*

class UserDashboardEditDetailsFragment: Fragment() {

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

        btnEditDetails.setOnClickListener {
            val directions = UserDashboardFragmentDirections.actionUserDashboardFragmentToUserDashboardEditDetailsFragment()
            findNavController().navigate(directions)
        }
    }



}