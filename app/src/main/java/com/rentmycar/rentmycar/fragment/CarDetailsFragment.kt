package com.rentmycar.rentmycar.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.airbnb.epoxy.EpoxyRecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.controller.CarDetailsEpoxyController
import com.rentmycar.rentmycar.domain.model.Location
import com.rentmycar.rentmycar.viewmodel.CarViewModel
import com.rentmycar.rentmycar.viewmodel.LocationViewModel

class CarDetailsFragment: Fragment() {

    var location: Location? = null
    private lateinit var mMap: GoogleMap
    private val viewModel: CarViewModel by lazy {
        ViewModelProvider(this)[CarViewModel::class.java]
    }

    private val locationViewModel: LocationViewModel by lazy {
        ViewModelProvider(this)[LocationViewModel::class.java]
    }

    private val epoxyController = CarDetailsEpoxyController()
    private val safeArgs: CarDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_car_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.carByIdLiveData.observe(viewLifecycleOwner) { car ->
            epoxyController.car = car
            if (car == null) {
                Toast.makeText(requireActivity(), RentMyCarApplication.context.getString(R.string.network_call_failed), Toast.LENGTH_LONG).show()
                return@observe
            }
        }

        locationViewModel.locationByIdLiveData.observe(viewLifecycleOwner) { location ->
            epoxyController.location = location
            if (location == null) {
                Toast.makeText(requireActivity(), RentMyCarApplication.context.getString(R.string.network_call_failed), Toast.LENGTH_LONG).show()
                return@observe
            }
        }

        val carId = safeArgs.carId
        viewModel.getCarById(id = carId)

        val epoxyRecyclerView = view.findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }
}