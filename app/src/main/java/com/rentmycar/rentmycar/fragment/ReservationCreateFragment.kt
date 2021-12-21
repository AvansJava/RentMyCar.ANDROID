package com.rentmycar.rentmycar.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.airbnb.epoxy.EpoxyRecyclerView
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.controller.ReservationCreateEpoxyController
import com.rentmycar.rentmycar.network.request.PostPaymentRequest
import com.rentmycar.rentmycar.network.request.ReservationNumberRequest
import com.rentmycar.rentmycar.viewmodel.PaymentViewModel
import com.rentmycar.rentmycar.viewmodel.ReservationViewModel
import kotlinx.android.synthetic.main.fragment_reservation_payment.*
import kotlinx.android.synthetic.main.model_reservation_create_payment_method.*

class ReservationCreateFragment: Fragment() {

    private val epoxyController = ReservationCreateEpoxyController(::onBtnBackClicked, ::onBtnPayClicked, ::dropdownFieldBinding)
    private val safeArgs: ReservationCreateFragmentArgs by navArgs()
    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private var paymentMethods: Array<String> = emptyArray()
    private val viewModel: ReservationViewModel by lazy {
        ViewModelProvider(this)[ReservationViewModel::class.java]
    }

    private val paymentViewModel: PaymentViewModel by lazy {
        ViewModelProvider(this)[PaymentViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reservation_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val reservationNumber = safeArgs.reservationNumber

        viewModel.reservationLiveData.observe(viewLifecycleOwner) { reservation ->
            epoxyController.reservation = reservation

            if (reservation == null) {
                Toast.makeText(requireActivity(), RentMyCarApplication.context.getString(R.string.network_call_failed), Toast.LENGTH_LONG).show()
                return@observe
            }
        }
        viewModel.getReservation(reservationNumber)

        observePayment()

        val epoxyRecyclerView = view.findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }

    private fun onBtnBackClicked() {
        val directions = ReservationCreateFragmentDirections.actionReservationCreateFragmentToCarListFragment()
        findNavController().navigate(directions)
    }

    private fun onBtnPayClicked(reservationNumber: String) {
        val paymentMethod = mapPaymentMethodToEnumValue(autoCompleteTextView.text.toString())

        if (!paymentMethods.contains(paymentMethod)) {
            autoCompleteTextView.error = RentMyCarApplication.context.getString(R.string.payment_method_empty)
        }
        autoCompleteTextView.error = null

        val paymentRequest = PostPaymentRequest(
            reservation = ReservationNumberRequest(reservationNumber),
            paymentMethod = paymentMethod
        )
        paymentViewModel.postPayment(paymentRequest)
    }

    private fun dropdownFieldBinding(autoCompleteTextView: AutoCompleteTextView) {
        this.autoCompleteTextView = autoCompleteTextView
        paymentMethods = resources.getStringArray(R.array.payment_methods)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.model_dropdown_list_item, paymentMethods)
        autoCompleteTextView.setAdapter(arrayAdapter)
    }

    private fun observePayment() {
        paymentViewModel.paymentLiveData.observe(viewLifecycleOwner) { payment ->
            if (payment != null) {
                val directions =
                    ReservationCreateFragmentDirections.actionReservationCreateFragmentToReservationPaymentFragment(paymentId = payment.id)
                findNavController().navigate(directions)
            }
        }
    }

    private fun mapPaymentMethodToEnumValue(paymentMethod: String): String {
        var paymentEnumType: String = ""

        when(true) {
            paymentMethod.contains("ideal", ignoreCase = true) -> {
                paymentEnumType = "IDEAL"
            }
            paymentMethod.contains("visa", ignoreCase = true) -> {
                paymentEnumType = "VISA_CARD"
            }
            paymentMethod.contains("mastercard", ignoreCase = true) -> {
                paymentEnumType = "MASTER_CARD"
            }
            paymentMethod.contains("paypal", ignoreCase = true) -> {
                paymentEnumType = "PAYPAL"
            }
            else -> {
                Toast.makeText(requireActivity(), RentMyCarApplication.context.getString(R.string.invalid_payment_method), Toast.LENGTH_LONG).show()
            }
        }
        return paymentEnumType
    }
}