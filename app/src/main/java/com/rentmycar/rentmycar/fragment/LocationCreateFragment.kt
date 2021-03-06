package com.rentmycar.rentmycar.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.room.Location
import com.rentmycar.rentmycar.viewmodel.CarViewModel
import com.rentmycar.rentmycar.viewmodel.LocationViewModel
import kotlinx.android.synthetic.main.fragment_location_create.*
import java.io.IOException
import java.util.*

class LocationCreateFragment: Fragment(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var address: Address? = null
    var gMap: GoogleMap? = null
    private val geoCoder = Geocoder(RentMyCarApplication.context, Locale.getDefault())
    private val safeArgs: LocationCreateFragmentArgs by navArgs()

    private val viewModel: LocationViewModel by lazy {
        ViewModelProvider(this)[LocationViewModel::class.java]
    }

    private val carViewModel: CarViewModel by lazy {
        ViewModelProvider(this)[CarViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_location_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val updateLocation = safeArgs.updateLocation
        val locationId = safeArgs.locationId
        val carId = safeArgs.carId

        viewModel.locationResult.observe(viewLifecycleOwner) { locationResult ->

            // Update car in room database with newly created location (part of car create)
            if (locationResult != null) {
                if (carId > 0 && locationResult > 0) {
                    carViewModel.updateCar(requireContext(), locationResult, carId)

                    val directions =
                        LocationCreateFragmentDirections.actionLocationCreateFragmentToCarCreateOverviewFragment(
                            locationId = locationResult,
                            carId = carId)
                    findNavController().navigate(directions)
                }
            }
        }

        checkPermissions()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        idSearchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchLocation(view)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        btnSelectLocation.setOnClickListener {
            if (address != null) {
                val location: com.rentmycar.rentmycar.domain.model.Location = parseAddress(address!!)

                when (true) {
                    updateLocation -> {
                        // If it is updating a location do a put request to api
                        viewModel.updateLocation(locationId, location)
                        val directions =
                            LocationCreateFragmentDirections.actionLocationCreateFragmentToLocationListFragment()
                        findNavController().navigate(directions)
                    } carId < 0 -> {
                        // If there is not carId this means a location is created separately from car.
                        // Do a post location request to API
                        viewModel.postLocation(location)
                    val directions =
                        LocationCreateFragmentDirections.actionLocationCreateFragmentToLocationListFragment()
                    findNavController().navigate(directions)
                    } else -> {
                        // Location is created in car creation process, store in room database to do post
                        // car with location in one at a later moment
                        val roomLocation = Location(
                            id = null,
                            street = location.street,
                            houseNumber = location.houseNumber,
                            postalCode = location.postalCode,
                            city = location.city,
                            country = location.country,
                            latitude = location.latitude,
                            longitude = location.longitude
                        )
                        viewModel.createLocation(requireContext(), roomLocation)
                    }
                }
            } else {
                Toast.makeText(RentMyCarApplication.context, RentMyCarApplication.context.getString(R.string.no_results_found), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun parseAddress(address: Address): com.rentmycar.rentmycar.domain.model.Location {

        return com.rentmycar.rentmycar.domain.model.Location(
            address.thoroughfare,
            address.featureName,
            address.postalCode,
            address.locality,
            address.countryName,
            address.latitude,
            address.longitude,
            id = null,
            userId = null
        )
    }

    // Search function in map. Search for address and add marker and zoom into it.
    fun searchLocation(view: View){
        val location: String = idSearchView.query.toString().trim()
        var addressList: List<Address>? = null

        if (location == ""){
            Toast.makeText(RentMyCarApplication.context, RentMyCarApplication.context.getString(R.string.no_search_query), Toast.LENGTH_SHORT).show()
        }else{
            try {
                addressList = geoCoder.getFromLocationName(location, 1)
            }catch (e: IOException){
                e.printStackTrace()
            }

            gMap?.clear()
            if (addressList == null || addressList.isEmpty()) {
                Toast.makeText(context,RentMyCarApplication.context.getString(R.string.no_results_found), Toast.LENGTH_LONG).show()
            } else {
                address = addressList[0]
                val latLng = LatLng(address!!.latitude, address!!.longitude)
                val addressLine = address!!.getAddressLine(0).toString()
                gMap?.addMarker(
                    MarkerOptions().position(latLng).title(addressLine))?.showInfoWindow()
                gMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
            }

        }
    }

    // Permissions are checked earlier
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        gMap = googleMap
        googleMap.isMyLocationEnabled = true
        with(gMap!!.uiSettings) {
            isZoomControlsEnabled = true
            isMyLocationButtonEnabled = true
        }
        googleMap.setOnMapClickListener(this)
    }

    override fun onMapClick(position: LatLng) {
        // Add marker by clicking on map
        gMap?.clear()
        var addressList: List<Address>? = null

        try {
            addressList = geoCoder.getFromLocation(position.latitude, position.longitude, 1)
        }catch (e: IOException){
            e.printStackTrace()
        }
        address = addressList!![0]
        val addressLine = address!!.getAddressLine(0).toString()
        gMap?.addMarker(
            MarkerOptions().position(position).title(addressLine))?.showInfoWindow()
        gMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 15f))
    }

    private fun checkPermissions() {
        // Check permissions and if none are found ask for them
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