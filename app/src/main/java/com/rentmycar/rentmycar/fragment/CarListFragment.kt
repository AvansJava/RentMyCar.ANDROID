package com.rentmycar.rentmycar.fragment

import android.os.Bundle
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
import com.rentmycar.rentmycar.controller.CarListEpoxyController
import com.rentmycar.rentmycar.viewmodel.CarViewModel

class CarListFragment: Fragment() {

    private val epoxyController = CarListEpoxyController(::onCarSelected)

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

        // Observe car list live data
        viewModel.carListLiveData.observe(viewLifecycleOwner) { cars ->
            epoxyController.cars = cars
            if (cars == null) {
                Toast.makeText(requireActivity(), RentMyCarApplication.context.getString(R.string.network_call_failed), Toast.LENGTH_LONG).show()
                return@observe
            }
        }
        viewModel.getCarsList()

        // Instantiate recycler view from epoxy controller
        val epoxyRecyclerView = view.findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }

    private fun onCarSelected(id: Int) {
        // When clicking on a car navigate to details page
        val directions = CarListFragmentDirections.actionCarListFragmentToCarDetailsFragment(carId = id)
        findNavController().navigate(directions)
    }
}