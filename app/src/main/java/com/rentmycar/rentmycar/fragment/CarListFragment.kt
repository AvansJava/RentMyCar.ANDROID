package com.rentmycar.rentmycar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.airbnb.epoxy.EpoxyRecyclerView
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.controller.CarListEpoxyController
import com.rentmycar.rentmycar.viewmodel.CarViewModel

class CarListFragment: Fragment() {

    private val epoxyController = CarListEpoxyController()

    private val viewModel: CarViewModel by lazy {
        ViewModelProvider(this)[CarViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_car_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.carListLiveData.observe(viewLifecycleOwner) { cars ->
            epoxyController.cars = cars
            if (cars == null) {
                Toast.makeText(requireActivity(), "Unsuccessful network call!", Toast.LENGTH_LONG).show()
                return@observe
            }
        }
        viewModel.getCarsList()

        val epoxyRecyclerView = view.findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }
}