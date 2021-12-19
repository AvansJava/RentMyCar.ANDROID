package com.rentmycar.rentmycar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.controller.UserLoginEpoxyController
import kotlinx.android.synthetic.main.fragment_user_dashboard.*

class UserDashboardEditDetailsFragment: Fragment() {

    private val epoxyController = UserLoginEpoxyController()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_dashboard_edit_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val epoxyRecyclerView = view.findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)

        btnUserDashboard.setOnClickListener {
            val directions = UserDashboardFragmentDirections.actionUserDashboardFragmentToUserDashboardEditDetailsFragment()
            findNavController().navigate(directions)
        }
    }

}