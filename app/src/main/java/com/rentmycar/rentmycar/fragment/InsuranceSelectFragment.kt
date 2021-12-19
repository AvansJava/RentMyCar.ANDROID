package com.rentmycar.rentmycar.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.airbnb.epoxy.EpoxyRecyclerView
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.controller.InsuranceSelectEpoxyController
import com.rentmycar.rentmycar.network.request.PostReservationRequest
import com.rentmycar.rentmycar.network.request.RentalPlanIdRequest
import com.rentmycar.rentmycar.network.request.TimeslotIdRequest
import com.rentmycar.rentmycar.viewmodel.InsuranceViewModel
import com.rentmycar.rentmycar.viewmodel.ReservationViewModel

class InsuranceSelectFragment: Fragment() {

    private val safeArgs: InsuranceSelectFragmentArgs by navArgs()
    private val epoxyController = InsuranceSelectEpoxyController(::onInsuranceSelected)
    private val insuranceViewModel: InsuranceViewModel by lazy {
        ViewModelProvider(this)[InsuranceViewModel::class.java]
    }
    private val reservationViewModel: ReservationViewModel by lazy {
        ViewModelProvider(this)[ReservationViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_insurance_select, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeInsurance()
        observeReservation()

        val epoxyRecyclerView = view.findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }

    private fun onInsuranceSelected(insuranceTypeId: String, insurancePrice: Double) {

        val reservation = PostReservationRequest(
            rentalPlan = RentalPlanIdRequest(id = safeArgs.rentalPlanId),
            insuranceTypeId = insuranceTypeId,
            insurancePrice = insurancePrice,
            timeslots = requireArguments().getParcelableArrayList<TimeslotIdRequest>("selectedTimeslots")!!.toList()
        )

        reservationViewModel.postReservation(reservation)
    }

    private fun observeInsurance() {
        insuranceViewModel.insuranceTypeListLiveData.observe(viewLifecycleOwner) { insuranceTypeList ->

            epoxyController.insuranceTypes = insuranceTypeList

            if (insuranceTypeList == null) {
                Toast.makeText(
                    requireActivity(),
                    RentMyCarApplication.context.getString(R.string.network_call_failed),
                    Toast.LENGTH_LONG
                ).show()
                return@observe
            }
        }
        insuranceViewModel.getInsuranceTypes()
    }

    private fun observeReservation() {
        reservationViewModel.reservationLiveData.observe(viewLifecycleOwner) { reservation ->
            if (reservation != null) {

//                todo implement parcelable

                val directions =
                    InsuranceSelectFragmentDirections.actionInsuranceSelectFragmentToReservationCreateFragment()
                findNavController().navigate(directions)
            }
        }
    }
}