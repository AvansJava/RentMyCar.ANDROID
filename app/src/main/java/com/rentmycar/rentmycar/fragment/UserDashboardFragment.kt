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
import com.rentmycar.rentmycar.AppPreference
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.controller.UserDashboardEpoxyController
import com.rentmycar.rentmycar.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_welcome_screen.*

class UserDashboardFragment: Fragment() {

    private val viewModel: UserViewModel by lazy {
        ViewModelProvider(this)[UserViewModel::class.java]
    }
    private val epoxyController = UserDashboardEpoxyController()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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

        btnRegister.setOnClickListener {
            val directions = UserDashboardFragmentDirections.actionUserDashboardFragmentToUserRegisterFragment()
            findNavController().navigate(directions)
        }
    }
}