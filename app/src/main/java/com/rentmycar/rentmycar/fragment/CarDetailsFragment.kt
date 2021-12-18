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
import com.google.android.gms.maps.*
import com.rentmycar.rentmycar.AppPreference
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.controller.CarDetailsEpoxyController
import com.rentmycar.rentmycar.viewmodel.CarViewModel
import kotlinx.android.synthetic.main.fragment_car_details.*
import kotlinx.android.synthetic.main.fragment_location_details.*
import kotlinx.android.synthetic.main.model_car_details_location_data_point.*
import kotlinx.android.synthetic.main.model_car_details_map.*
import kotlinx.android.synthetic.main.model_car_details_title.*

class CarDetailsFragment: Fragment() {

    private val userId = AppPreference(RentMyCarApplication.context).getUserId()
    private var locationId: Int? = null
    private var carId: Int? = null
    private val viewModel: CarViewModel by lazy {
        ViewModelProvider(this)[CarViewModel::class.java]
    }

    private val epoxyController = CarDetailsEpoxyController(::onLocationBtnClicked,
        ::onEditLocationBtnClicked, ::onEditCarBtnClicked, ::onBookNowBtnClicked)
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

            if (userId == car.userId) {
                btnAddResource.visibility = View.VISIBLE
            } else {
                btnAddResource.visibility = View.GONE
            }

            locationId = car.location?.id
            carId = car.id
        }

        val carId = safeArgs.carId
        viewModel.getCarById(id = carId)

        val epoxyRecyclerView = view.findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }

    private fun onLocationBtnClicked(id: Int) {
        val directions =
            CarDetailsFragmentDirections.actionCarDetailsFragmentToLocationDetailsFragment(id)
        findNavController().navigate(directions)
    }

    private fun onEditLocationBtnClicked(id: Int) {
        val directions =
            CarDetailsFragmentDirections.actionCarDetailsFragmentToLocationCreateFragment(true, id)
        findNavController().navigate(directions)
    }

    private fun onEditCarBtnClicked(id: Int) {
        val directions =
            CarDetailsFragmentDirections.actionCarDetailsFragmentToCarCreateFragment(updateCar = true, id)
        findNavController().navigate(directions)
    }

    private fun onBookNowBtnClicked(id: Int) {
        val directions =
            CarDetailsFragmentDirections.actionCarDetailsFragmentToCarAvailabilityFragment(id)
        findNavController().navigate(directions)
    }
}

