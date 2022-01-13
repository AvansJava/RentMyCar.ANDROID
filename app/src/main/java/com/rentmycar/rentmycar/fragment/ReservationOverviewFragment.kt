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
import com.rentmycar.rentmycar.controller.ReservationOverviewEpoxyController
import com.rentmycar.rentmycar.viewmodel.ReservationViewModel

class ReservationOverviewFragment: Fragment() {

    val epoxyController = ReservationOverviewEpoxyController(::onReservationSelected)

    private val viewModel: ReservationViewModel by lazy {
        ViewModelProvider(this)[ReservationViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reservation_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.reservationListLiveData.observe(viewLifecycleOwner) { reservations ->
            epoxyController.reservations = reservations
            if (reservations == null) {
                Toast.makeText(requireActivity(), RentMyCarApplication.context.getString(R.string.network_call_failed), Toast.LENGTH_LONG).show()
                return@observe
            }
        }
        viewModel.getReservationList(status = null)

        val epoxyRecyclerView = view.findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }

    private fun onReservationSelected(reservationNumber: String, status: String) {

        // If status is pending send property to allow to still complete reservation in next step
        val isDetailsView = status != "PENDING"

        val directions =
            ReservationOverviewFragmentDirections.actionReservationOverviewFragmentToReservationCreateFragment(
                reservationNumber,
                isDetailsView
            )
        findNavController().navigate(directions)
    }
}