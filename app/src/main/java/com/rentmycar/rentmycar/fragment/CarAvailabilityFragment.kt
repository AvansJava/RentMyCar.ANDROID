package com.rentmycar.rentmycar.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.airbnb.epoxy.EpoxyRecyclerView
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.controller.CarAvailabilityEpoxyController
import com.rentmycar.rentmycar.viewmodel.AvailabilityViewModel
import com.rentmycar.rentmycar.viewmodel.factory.AvailabilityViewModelFactory

class CarAvailabilityFragment: Fragment() {

    private val safeArgs: CarAvailabilityFragmentArgs by navArgs()
    private val epoxyController = CarAvailabilityEpoxyController()
    private lateinit var viewModel: AvailabilityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_car_availability, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(requireActivity(), AvailabilityViewModelFactory(carId = safeArgs.carId))[AvailabilityViewModel::class.java]

        viewModel.availabilityPagedListLiveData.observe(viewLifecycleOwner) { pagedList ->
            epoxyController.submitList(pagedList)
        }

        view.findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView).setController(epoxyController)
    }
}
