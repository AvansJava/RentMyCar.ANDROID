package com.rentmycar.rentmycar.fragment

import android.app.Activity
import android.content.Intent
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
import com.github.dhaval2404.imagepicker.ImagePicker
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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody

import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class CarDetailsFragment: Fragment() {

    private val userId = AppPreference(RentMyCarApplication.context).getUserId()
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
        }

        observeCarResource()

        val carId = safeArgs.carId
        viewModel.getCarById(id = carId)

        btnAddResource.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .start(101)
        }

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

    private fun onBookNowBtnClicked(carId: Int, rentalPlanId: Int) {
        val directions =
            CarDetailsFragmentDirections.actionCarDetailsFragmentToCarAvailabilityFragment(carId = carId, rentalPlanId = rentalPlanId)
        findNavController().navigate(directions)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            val uri = data?.data
            val file = File(uri?.path)
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val image = MultipartBody.Part.createFormData("file","file", requestFile)

            Toast.makeText(requireActivity(), RentMyCarApplication.context.getString(R.string.uploading_image), Toast.LENGTH_LONG).show()

            viewModel.postCarResource(safeArgs.carId, image)
        }
    }

    private fun observeCarResource() {
        viewModel.carResourceResult.observe(viewLifecycleOwner) { response ->
            if (response == null) {
                Toast.makeText(requireActivity(), RentMyCarApplication.context.getString(R.string.network_call_failed), Toast.LENGTH_LONG).show()
                return@observe
            }

            Toast.makeText(requireActivity(), RentMyCarApplication.context.getString(R.string.image_uploaded), Toast.LENGTH_LONG).show()

            val directions = CarDetailsFragmentDirections.actionCarDetailsFragmentToCarDetailsFragment(carId = safeArgs.carId)
            findNavController().navigate(directions)
        }
    }
}

