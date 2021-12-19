package com.rentmycar.rentmycar.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.airbnb.epoxy.EpoxyRecyclerView
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.controller.CarAvailabilityEpoxyController
import com.rentmycar.rentmycar.network.request.TimeslotIdRequest
import com.rentmycar.rentmycar.viewmodel.AvailabilityViewModel
import com.rentmycar.rentmycar.viewmodel.factory.AvailabilityViewModelFactory
import kotlinx.android.synthetic.main.fragment_car_availability.*

class CarAvailabilityFragment: Fragment() {

    private val safeArgs: CarAvailabilityFragmentArgs by navArgs()
    private val epoxyController = CarAvailabilityEpoxyController(::timeslotSelected)
    private lateinit var viewModel: AvailabilityViewModel
    private var selectedTimeslots: MutableList<TimeslotIdRequest> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_car_availability, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (selectedTimeslots.size == 0) {
            btnBookNow.isEnabled = false
        }

        viewModel =
            ViewModelProvider(requireActivity(), AvailabilityViewModelFactory(carId = safeArgs.carId))[AvailabilityViewModel::class.java]

        viewModel.availabilityPagedListLiveData.observe(viewLifecycleOwner) { pagedList ->
            epoxyController.submitList(pagedList)
        }

        btnBookNow.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelableArrayList("selectedTimeslots", ArrayList<TimeslotIdRequest>(selectedTimeslots))
            bundle.putInt("rentalPlanId", safeArgs.rentalPlanId)

            val insuranceFragment = InsuranceSelectFragment()
            insuranceFragment.arguments = bundle

            val fragmentTransaction = childFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.availability_layout, insuranceFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        view.findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView).setController(epoxyController)
    }

    private fun timeslotSelected(id: Int) {
        selectedTimeslots.add(TimeslotIdRequest(id = id))
        btnBookNow.isEnabled = true
    }
}
