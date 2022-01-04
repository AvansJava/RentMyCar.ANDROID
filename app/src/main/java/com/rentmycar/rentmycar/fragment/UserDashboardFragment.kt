package com.rentmycar.rentmycar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.controller.UserDashboardEpoxyController
import com.rentmycar.rentmycar.network.response.GetUserResponse
import com.rentmycar.rentmycar.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_user_dashboard.*
import kotlinx.android.synthetic.main.fragment_user_register.*

class UserDashboardFragment: Fragment() {

    private val viewModel: UserViewModel by lazy {
        ViewModelProvider(this)[UserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val epoxyController = UserDashboardEpoxyController(::onEditDetailsClicked)

        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        viewModel.getUserLiveData.observe(viewLifecycleOwner) { user ->
            epoxyController.getUserResponse = user

            if (user == null) {
                Toast.makeText(requireActivity(), RentMyCarApplication.context.getString(R.string.email_password_incorrect), Toast.LENGTH_LONG).show()
                navController.navigate(R.id.userLoginFragment)
            }
        }
        viewModel.getUser()

        val epoxyRecyclerView = view.findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)

    }

    private fun onEditDetailsClicked(
        firstName: String,
        lastName: String,
        streetName: String,
        houseNumber: String,
        city: String,
        postalCode: String,
        country: String,
        phoneNumber: String,
        emailAddress: String
    ) {
        val directions =
            UserDashboardFragmentDirections.actionUserDashboardFragmentToUserDashboardEditDetailsFragment(
                firstName,
                lastName,
                streetName,
                houseNumber,
                city,
                postalCode,
                country,
                phoneNumber,
                emailAddress
            )
        findNavController().navigate(directions)
    }
}

