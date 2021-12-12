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
import com.airbnb.epoxy.EpoxyRecyclerView
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.controller.LocationListEpoxyController
import com.rentmycar.rentmycar.viewmodel.LocationViewModel
import kotlinx.android.synthetic.main.fragment_location_list.*

class LocationListFragment: Fragment() {

    private val epoxyController = LocationListEpoxyController(::onLocationSelected)
    private val viewModel: LocationViewModel by lazy {
        ViewModelProvider(this)[LocationViewModel::class.java]
}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_location_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.locationsListLiveData.observe(viewLifecycleOwner) { locations ->
            epoxyController.locations = locations
            if (locations == null) {
                Toast.makeText(requireActivity(), RentMyCarApplication.context.getString(R.string.network_call_failed), Toast.LENGTH_LONG).show()
                return@observe
            }
        }
        viewModel.getLocations()

        locationAdd.setOnClickListener {
            val directions = LocationListFragmentDirections.actionLocationListFragmentToLocationCreateFragment()
            findNavController().navigate(directions)
        }

        val epoxyRecyclerView = view.findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }

    private fun onLocationSelected(id: Int) {
        val directions = LocationListFragmentDirections.actionLocationListFragmentToLocationDetailsFragment(locationId = id)
        findNavController().navigate(directions)
    }
}