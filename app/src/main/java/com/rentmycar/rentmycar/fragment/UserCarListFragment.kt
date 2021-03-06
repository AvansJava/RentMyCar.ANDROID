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
import com.rentmycar.rentmycar.controller.UserCarListEpoxyController
import com.rentmycar.rentmycar.viewmodel.CarViewModel
import kotlinx.android.synthetic.main.fragment_user_car_list.*

class UserCarListFragment: Fragment() {

    private val epoxyController = UserCarListEpoxyController(::onCarSelected)

    private val viewModel: CarViewModel by lazy {
        ViewModelProvider(this)[CarViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_car_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.carListLiveData.observe(viewLifecycleOwner) { cars ->
            epoxyController.cars = cars
            if (cars == null) {
                Toast.makeText(requireActivity(), RentMyCarApplication.context.getString(R.string.network_call_failed), Toast.LENGTH_LONG).show()
                return@observe
            }
        }
        viewModel.getCarsByUser()

        btnCarAdd.setOnClickListener {
            val directions = UserCarListFragmentDirections.actionUserCarListFragmentToCarCreateFragment()
            findNavController().navigate(directions)
        }

        val epoxyRecyclerView = view.findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }

    private fun onCarSelected(id: Int) {
        val directions = UserCarListFragmentDirections.actionUserCarListFragmentToCarDetailsFragment(carId = id)
        findNavController().navigate(directions)
    }
}