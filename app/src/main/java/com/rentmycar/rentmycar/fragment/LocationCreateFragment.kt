package com.rentmycar.rentmycar.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rentmycar.rentmycar.R
import kotlinx.android.synthetic.main.fragment_location_create.*

class LocationCreateFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_location_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        testMapsButton.setOnClickListener {
            val directions = LocationCreateFragmentDirections.actionLocationCreateFragmentToLocationMapsFragment()
            findNavController().navigate(directions)
        }
    }
}