package com.rentmycar.rentmycar.fragment

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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.controller.RentalPlanDetailsEpoxyController
import com.rentmycar.rentmycar.network.request.PutTimeslotRequest
import com.rentmycar.rentmycar.viewmodel.AvailabilityViewModel
import com.rentmycar.rentmycar.viewmodel.RentalPlanViewModel
import com.rentmycar.rentmycar.viewmodel.factory.AvailabilityViewModelFactory
import kotlinx.android.synthetic.main.fragment_rental_plan_details.*
import java.text.SimpleDateFormat
import java.util.*


class RentalPlanDetailsFragment: Fragment() {

    private val epoxyController = RentalPlanDetailsEpoxyController(::timeslotSelected)
    private val safeArgs: RentalPlanDetailsFragmentArgs by navArgs()

    private val viewModel: RentalPlanViewModel by lazy {
        ViewModelProvider(this)[RentalPlanViewModel::class.java]
    }
    private lateinit var availabilityViewModel: AvailabilityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rental_plan_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.rentalPlanByIdLiveData.observe(viewLifecycleOwner) { rentalPlan ->
            if (rentalPlan == null) {
                Toast.makeText(requireActivity(), RentMyCarApplication.context.getString(R.string.network_call_failed), Toast.LENGTH_LONG).show()
                return@observe
            }

            val carName = RentMyCarApplication.context.getString(R.string.car_brand_model, rentalPlan.car?.brand, rentalPlan.car?.brandType, rentalPlan.car?.model)

            rentalPlanItalicTextView.text = carName
            carTextView.text = carName
            availableFromTextView.text = convertDate(rentalPlan.availableFrom)
            availableUntilTextView.text = convertDate(rentalPlan.availableUntil)
            priceTextView.text = RentMyCarApplication.context.getString(R.string.rental_plan_price, rentalPlan.price)

        }
        viewModel.getRentalPlanById(safeArgs.rentalPlanId)

        availabilityViewModel =
            ViewModelProvider(this, AvailabilityViewModelFactory(carId = safeArgs.carId))[AvailabilityViewModel::class.java]

        availabilityViewModel.availabilityPagedListLiveData.observe(viewLifecycleOwner) { pagedList ->
            epoxyController.submitList(pagedList)
        }
        availabilityViewModel.timeslotLiveData.observe(viewLifecycleOwner) { timeslot ->
            if (timeslot == null) {
                Toast.makeText(requireActivity(), RentMyCarApplication.context.getString(R.string.network_call_failed), Toast.LENGTH_LONG).show()
                return@observe
            }
            Toast.makeText(requireActivity(), RentMyCarApplication.context.getString(R.string.timeslot_status_updated), Toast.LENGTH_LONG).show()

            val directions = RentalPlanDetailsFragmentDirections.actionRentalPlanDetailsFragmentToRentalPlanListFragment()
            findNavController().navigate(directions)
        }

        view.findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView).setController(epoxyController)
    }

    private fun timeslotSelected(id: Int, startAt: String, status: String, productId: Int?) {

        if (productId == null) {
            val dialogMessage: String
            val positiveBtnText: String

            // Show dialog message depending on opening or closing timeslot
            if (status == "OPEN") {
                dialogMessage = resources.getString(R.string.timeslot_close_message, startAt)
                positiveBtnText = resources.getString(R.string.close_timeslot)
            } else {
                dialogMessage = resources.getString(R.string.timeslot_open_message, startAt)
                positiveBtnText = resources.getString(R.string.open_timeslot)
            }

            MaterialAlertDialogBuilder(requireContext())
                .setMessage(dialogMessage)
                .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
                    dialog.dismiss()
                }
                .setPositiveButton(positiveBtnText) { dialog, which ->
                    val timeslotRequest: PutTimeslotRequest

                    // If timeslot is open, set to closed and vice versa
                    if (status == "OPEN") {
                        timeslotRequest = PutTimeslotRequest(status = "CLOSED")
                    } else {
                        timeslotRequest = PutTimeslotRequest(status = "OPEN")
                    }

                    availabilityViewModel.updateTimeslotStatus(id, timeslotRequest)
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun convertDate(input: String): String {
        val date = SimpleDateFormat("yyyy-MM-dd",
            Locale.getDefault()).parse(input)
        val format = SimpleDateFormat(
            "dd-MM-yyyy",
            Locale.getDefault())
        return format.format(date!!)
    }
}

