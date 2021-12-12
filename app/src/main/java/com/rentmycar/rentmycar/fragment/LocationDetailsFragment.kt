package com.rentmycar.rentmycar.fragment

import android.Manifest
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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.domain.model.Location
import com.rentmycar.rentmycar.viewmodel.LocationViewModel
import kotlinx.android.synthetic.main.fragment_location_details.*
import kotlinx.coroutines.*

class LocationDetailsFragment: Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var selectedLocation: Location? = null
    private val viewModel: LocationViewModel by lazy {
        ViewModelProvider(this)[LocationViewModel::class.java]
    }
    private val safeArgs: LocationDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_location_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.locationByIdLiveData.observe(viewLifecycleOwner) { location ->
            selectedLocation = location
            if (location == null) {
                Toast.makeText(requireActivity(), RentMyCarApplication.context.getString(R.string.network_call_failed), Toast.LENGTH_LONG).show()
                return@observe
            }

            val latLng = LatLng(
                selectedLocation!!.latitude,
                selectedLocation!!.longitude
            )

            mMap.addMarker(
                MarkerOptions().position(latLng)
                    .title(selectedLocation!!.street + " " + selectedLocation!!.houseNumber)
            )?.showInfoWindow()
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))

            locationTextView.text = RentMyCarApplication.context.getString(R.string.location_details_header,
                location.street, location.houseNumber, location.city)
            addressLine.text = RentMyCarApplication.context.getString(R.string.address_line,
                location.street, location.houseNumber)
            postalCodeLine.text = location.postalCode
            cityLine.text = location.city
            countryLine.text = location.country
        }
        val locationId = safeArgs.locationId
        viewModel.getLocationById(id = locationId)

        checkPermissions()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        btnUpdateLocation.setOnClickListener {
            val directions = LocationDetailsFragmentDirections.actionLocationDetailsFragmentToLocationCreateFragment(true, locationId)
            findNavController().navigate(directions)
        }

        btnDeleteLocation.setOnClickListener {
            viewModel.deleteLocation(locationId)

            val directions = LocationDetailsFragmentDirections.actionLocationDetailsFragmentToLocationListFragment()
            findNavController().navigate(directions)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ), 101)
        }
    }
}