package com.rentmycar.rentmycar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rentmycar.rentmycar.AppPreference
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.room.Car
import com.rentmycar.rentmycar.viewmodel.CarViewModel
import kotlinx.android.synthetic.main.fragment_car_create.*

class CarCreateFragment: Fragment() {

    private val preference = AppPreference(RentMyCarApplication.context)
    private val safeArgs: CarCreateFragmentArgs by navArgs()
    private val viewModel: CarViewModel by lazy {
        ViewModelProvider(this)[CarViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()

        val carTypes = resources.getStringArray(R.array.car_types)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.model_dropdown_list_item, carTypes)
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

        val updateCar = safeArgs.updateCar
        val carId = safeArgs.carId

        if (updateCar) {
            btnNext.text = RentMyCarApplication.context.getString(R.string.update_car)
        }

        viewModel.carResult.observe(viewLifecycleOwner) { carResult ->
            if (carResult == null) {
                Toast.makeText(requireActivity(), RentMyCarApplication.context.getString(R.string.network_call_failed), Toast.LENGTH_LONG).show()
                return@observe
            }
            val directions = CarCreateFragmentDirections.actionCarCreateFragmentToLocationCreateFragment(carId = carResult.toInt())
            findNavController().navigate(directions)
        }

        btnNext.setOnClickListener {
            val car = Car(
                id = null,
                brand = car_brand.editText?.text?.trim { it <= ' ' }.toString(),
                brandType = car_brand_type.editText?.text?.trim { it <= ' ' }.toString(),
                model = car_model.editText?.text?.trim { it <= ' ' }.toString(),
                licensePlateNumber = car_license_plate.editText?.text?.trim { it <= ' ' }.toString(),
                carType = autoCompleteTextView.text.toString(),
                consumption = car_consumption.editText?.text?.trim { it <= ' ' }.toString().replace(",",".").toDouble(),
                costPrice = car_cost_price.editText?.text?.trim { it <= ' ' }.toString().replace(",",".").toDouble().toInt(),
                userId = preference.getUserId(),
                locationId = null
            )
            if (updateCar) {
                val carType = getCarType(car.carType)
                viewModel.putCar(carId, com.rentmycar.rentmycar.domain.model.Car(
                    id = 0,
                    brand = car.brand,
                    brandType = car.brandType,
                    model = car.model,
                    licensePlateNumber = car.licensePlateNumber,
                    carType = carType,
                    consumption = car.consumption,
                    costPrice = car.costPrice,
                    userId = null,
                    createdAt = null,
                    updatedAt = null,
                    resources = null,
                    location = null,
                    rentalPlan = null
                ))
                val directions = CarCreateFragmentDirections.actionCarCreateFragmentToCarDetailsFragment(carId)
                findNavController().navigate(directions)
            } else {
                viewModel.createCar(requireContext(), car)
            }
        }
    }

    private fun getCarType(carType: String): String {
        return when (true) {
            carType.contains("ICE") -> {
                "ICE"
            }
            carType.contains("FCEV") -> {
                "FCEV"
            }
            else -> {
                "BEV"
            }
        }
    }
}