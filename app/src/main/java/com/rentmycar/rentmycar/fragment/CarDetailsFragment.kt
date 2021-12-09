package com.rentmycar.rentmycar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.airbnb.epoxy.EpoxyRecyclerView
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.controller.CarDetailsEpoxyController
import com.rentmycar.rentmycar.viewmodel.CarViewModel

class CarDetailsFragment: Fragment() {

    private val viewModel: CarViewModel by lazy {
        ViewModelProvider(this)[CarViewModel::class.java]
    }

    private val epoxyController = CarDetailsEpoxyController()
    private val safeArgs: CarDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_car_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.carByIdLiveData.observe(viewLifecycleOwner) { car ->
//            epoxyController.car = car
            if (car == null) {
                Toast.makeText(requireActivity(), "Unsuccessful network call!", Toast.LENGTH_LONG).show()
                return@observe
            }
        }

        val carId = safeArgs.carId
        viewModel.getCarById(id = carId)

        val epoxyRecyclerView = view.findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }
}