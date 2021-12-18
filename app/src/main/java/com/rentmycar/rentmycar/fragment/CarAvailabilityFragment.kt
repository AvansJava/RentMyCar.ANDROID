package com.rentmycar.rentmycar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.controller.CarAvailabilityEpoxyController

class CarAvailabilityFragment: Fragment() {

    private val safeArgs: CarAvailabilityFragmentArgs by navArgs()
    private val epoxyController = CarAvailabilityEpoxyController()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_car_availability, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}
