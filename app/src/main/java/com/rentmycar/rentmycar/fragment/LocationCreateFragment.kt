package com.rentmycar.rentmycar.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.viewmodel.LocationViewModel
import kotlinx.android.synthetic.main.fragment_location_create.*
import java.io.IOException
import java.util.*

class LocationCreateFragment: Fragment(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var address: Address? = null
    var gMap: GoogleMap? = null
    private val geoCoder = Geocoder(RentMyCarApplication.context, Locale.getDefault())

    private val viewModel: LocationViewModel by lazy {
        ViewModelProvider(this)[LocationViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_location_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                viewModel.postLocation(location)

                val directions =
                    LocationCreateFragmentDirections.actionLocationCreateFragmentToLocationDetailsFragment()
                findNavController().navigate(directions)
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
            address.latitude.toFloat(),
            address.longitude.toFloat(),
            id = null
        )
    }

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

}