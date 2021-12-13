package com.rentmycar.rentmycar.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.rentmycar.rentmycar.R
import kotlinx.android.synthetic.main.fragment_car_create.*

class CarCreateFragment: Fragment() {

    override fun onResume() {
        super.onResume()

        val carTypes = resources.getStringArray(R.array.car_types)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.model_car_type_list_item, carTypes)
        autoCompleteTextView.setAdapter(arrayAdapter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_car_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}