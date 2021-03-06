package com.rentmycar.rentmycar.fragment

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.network.request.CarIdRequest
import com.rentmycar.rentmycar.network.request.PostRentalPlanRequest
import com.rentmycar.rentmycar.viewmodel.CarViewModel
import com.rentmycar.rentmycar.viewmodel.RentalPlanViewModel
import kotlinx.android.synthetic.main.fragment_car_create.*
import kotlinx.android.synthetic.main.fragment_car_create.autoCompleteTextView
import kotlinx.android.synthetic.main.fragment_rental_plan_create.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.logging.SimpleFormatter

class RentalPlanCreateFragment: Fragment() {

    var carList: Array<String> = emptyArray()

    private val carViewModel: CarViewModel by lazy {
        ViewModelProvider(this)[CarViewModel::class.java]
    }

    private val viewModel: RentalPlanViewModel by lazy {
        ViewModelProvider(this)[RentalPlanViewModel::class.java]
    }

    private val constraintsBuilder =
        CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())

    private val dateRangePicker =
        MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText(RentMyCarApplication.context.getString(R.string.select_dates))
            .setSelection(
                Pair(
                    MaterialDatePicker.thisMonthInUtcMilliseconds(),
                    MaterialDatePicker.todayInUtcMilliseconds()
                )
            )
            .setCalendarConstraints(constraintsBuilder.build())
            .build()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rental_plan_create, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUserCarList()

        // Fields cannot be typed in, instead add onClick listener that shows dateRangePicker and fills date
        start_field.isFocusable = false
        end_field.isFocusable = false
        start_field.setOnClickListener {
            dateRangePicker.show(childFragmentManager, "date_picker")
        }
        end_field.setOnClickListener {
            dateRangePicker.show(childFragmentManager, "date_picker")
        }

        dateRangePicker.addOnPositiveButtonClickListener { dateSelected ->
            start_field.text = SpannableStringBuilder(convertLongToTime(dateSelected.first))
            end_field.text = SpannableStringBuilder(convertLongToTime(dateSelected.second))
        }

        btnSubmit.setOnClickListener {
            // Set and post rentalPlan object.
            val stringArray = autoCompleteTextView.text.split("-").toTypedArray()
            val carId = stringArray[0].trim { it <= ' ' }.toInt()

            val carObject = CarIdRequest(
                id = carId
            )
            val rentalPlan = PostRentalPlanRequest(
                car = carObject,
                availableFrom = start_field.text.toString(),
                availableUntil = end_field.text.toString(),
                price = price_rental_plan.editText?.text.toString().replace(",",".").toDouble()
            )
            viewModel.postRentalPlan(rentalPlan)

            val directions = RentalPlanCreateFragmentDirections.actionRentalPlanCreateFragmentToRentalPlanListFragment()
            findNavController().navigate(directions)
        }
    }

    private fun observeUserCarList() {
        // Retrieve cars from user and add them to a dropdown
        carViewModel.carListLiveData.observe(viewLifecycleOwner) { cars ->
            if (cars == null) {
                Toast.makeText(requireActivity(), RentMyCarApplication.context.getString(R.string.network_call_failed), Toast.LENGTH_LONG).show()
                return@observe
            }

            cars.forEach { car ->
                val carName = RentMyCarApplication.context.getString(R.string.car_list, car?.id, car?.brand, car?.brandType, car?.model)
                carList = append(carList, carName)
            }

            val arrayAdapter = ArrayAdapter(requireContext(), R.layout.model_dropdown_list_item, carList)
            autoCompleteTextView.setAdapter(arrayAdapter)
        }
        carViewModel.getCarsByUser()
    }

    private fun append(arr: Array<String>, element: String): Array<String> {
        val list: MutableList<String> = arr.toMutableList()
        list.add(element)
        return list.toTypedArray()
    }

    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.getDefault())
        return format.format(date)
    }
}