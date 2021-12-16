package com.rentmycar.rentmycar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.airbnb.epoxy.EpoxyRecyclerView
import com.rentmycar.rentmycar.AppPreference
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.controller.CarCreateOverviewEpoxyController
import com.rentmycar.rentmycar.domain.model.Car
import com.rentmycar.rentmycar.domain.model.Location
import com.rentmycar.rentmycar.viewmodel.CarViewModel
import com.rentmycar.rentmycar.viewmodel.LocationViewModel
import kotlinx.android.synthetic.main.model_car_create_overview_buttons.*

class CarCreateOverviewFragment: Fragment() {

    private val safeArgs: LocationCreateFragmentArgs by navArgs()
    private val epoxyController = CarCreateOverviewEpoxyController(::onContinueSelected)
    private val userId = AppPreference(RentMyCarApplication.context).getUserId()
    private var newLocation: Location? = null
    private lateinit var newCar: Car

    private val viewModel: CarViewModel by lazy {
        ViewModelProvider(this)[CarViewModel::class.java]
    }
    private val locationViewModel: LocationViewModel by lazy {
        ViewModelProvider(this)[LocationViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_car_create_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCarAndLocation()
        observeLocationAndCar()

        val epoxyRecyclerView = view.findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }

    private fun observeLocationAndCar() {
        locationViewModel.locationRoomLiveData.observe(viewLifecycleOwner) { location ->
            epoxyController.location = location

            if (location == null) {
                Toast.makeText(requireActivity(), RentMyCarApplication.context.getString(R.string.network_call_failed), Toast.LENGTH_LONG).show()
                return@observe
            }

            newLocation = Location(
                id = null,
                street = location.street,
                houseNumber = location.houseNumber,
                postalCode = location.postalCode,
                city = location.city,
                country = location.country,
                latitude = location.latitude,
                longitude = location.longitude,
                userId = userId
            )

            viewModel.carRoomLiveData.observe(viewLifecycleOwner) { car ->
                epoxyController.car = car

                if (car == null) {
                    Toast.makeText(requireActivity(), RentMyCarApplication.context.getString(R.string.network_call_failed), Toast.LENGTH_LONG).show()
                }

                if (newLocation != null) {
                    var carType: String = ""
                    if (car != null) {
                        carType = when (true) {
                            car.carType.contains("ICE") -> {
                                "ICE"
                            }
                            car.carType.contains("FCEV") -> {
                                "FCEV"
                            }
                            else -> {
                                "BEV"
                            }
                        }
                    }

                    if (car != null) {
                        newCar = Car(
                            id = 0,
                            brand = car.brand,
                            brandType = car.brandType,
                            model = car.model,
                            licensePlateNumber = car.licensePlateNumber,
                            consumption = car.consumption,
                            costPrice = car.costPrice,
                            carType = carType,
                            userId = userId,
                            location = newLocation,
                            createdAt = null,
                            updatedAt = null,
                            resources = null,
                            rentalPlan = null
                        )
                    }
                    viewModel.carByIdLiveData.observe(viewLifecycleOwner) { createdCar ->
                        epoxyController.createdCar = createdCar

                    }
                    viewModel.postCar(newCar)
                }
            }
        }
    }

    private fun getCarAndLocation() {
        viewModel.getCar(requireContext(), safeArgs.carId)
        locationViewModel.getLocation(requireContext(), safeArgs.locationId)
    }

    private fun onContinueSelected() {
        val directions = CarCreateOverviewFragmentDirections.actionCarCreateOverviewFragmentToUserCarListFragment()
        findNavController().navigate(directions)
    }
}