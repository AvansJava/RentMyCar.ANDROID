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
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.network.request.PostPaymentCallbackRequest
import com.rentmycar.rentmycar.network.response.GetPaymentResponse
import com.rentmycar.rentmycar.viewmodel.PaymentViewModel
import kotlinx.android.synthetic.main.fragment_reservation_payment.*

class ReservationPaymentFragment: Fragment() {

    private val safeArgs: ReservationPaymentFragmentArgs by navArgs()
    private var paymentCallbackStatus: String? = null

    private val viewModel: PaymentViewModel by lazy {
        ViewModelProvider(this)[PaymentViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reservation_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.paymentLiveData.observe(viewLifecycleOwner) { payment ->
            if (payment == null) {
                Toast.makeText(requireActivity(), RentMyCarApplication.context.getString(R.string.network_call_failed), Toast.LENGTH_LONG).show()
                return@observe
            }
            setBindings(payment)

            btnFailed.setOnClickListener {
                paymentCallbackStatus = "CANCELED"

                postPaymentCallback(payment, paymentCallbackStatus!!)
            }

            btnSuccess.setOnClickListener {
                paymentCallbackStatus = "SUCCESS"

                postPaymentCallback(payment, paymentCallbackStatus!!)
            }
        }
        viewModel.getPayment(safeArgs.paymentId)

        viewModel.paymentCallbackLiveData.observe(viewLifecycleOwner) { callback ->

            if (callback == null) {
                Toast.makeText(requireActivity(), RentMyCarApplication.context.getString(R.string.payment_callback_failed), Toast.LENGTH_LONG).show()
                return@observe
            }

            val directions =
                ReservationPaymentFragmentDirections.actionReservationPaymentFragmentToReservationOverviewFragment()
            findNavController().navigate(directions)
        }
    }

    private fun setBindings(payment: GetPaymentResponse) {
        paymentTitle.text = RentMyCarApplication.context.getString(R.string.payment_of_amount, payment.price)
        reservationLine.text = payment.reservationNumber
        priceLine.text = RentMyCarApplication.context.getString(R.string.price_amount_reservation, payment.price)

        when(payment.paymentMethod) {
            "IDEAL" -> {
                paymentMethodLine.text =
                    RentMyCarApplication.context.getString(R.string.ideal)
            }
            "VISA_CARD" -> {
                paymentMethodLine.text =
                    RentMyCarApplication.context.getString(R.string.visa_card)
            }
            "PAYPAL" -> {
                paymentMethodLine.text =
                    RentMyCarApplication.context.getString(R.string.paypal)
            }
            "MASTER_CARD" -> {
                paymentMethodLine.text =
                    RentMyCarApplication.context.getString(R.string.mastercard)
            }
        }

        when(payment.paymentStatus) {
            "COMPLETED" -> {
                statusLine.text =
                    RentMyCarApplication.context.getString(R.string.status_completed)
                status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_check_circle_24, 0, 0, 0)
            }
            "PENDING" -> {
                statusLine.text =
                    RentMyCarApplication.context.getString(R.string.status_pending)
                status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_access_time_24, 0, 0, 0)
            }
            "CANCELED" -> {
                statusLine.text =
                    RentMyCarApplication.context.getString(R.string.status_canceled)
                status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_cancel_24, 0, 0, 0)
            }
            "EXPIRED" -> {
                statusLine.text =
                    RentMyCarApplication.context.getString(R.string.status_expired)
                status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_cancel_24, 0, 0, 0)
            }
        }
    }

    private fun postPaymentCallback(payment: GetPaymentResponse, paymentCallbackStatus: String) {
        val callbackRequest = PostPaymentCallbackRequest(
            externalReference = payment.reservationNumber,
            status = paymentCallbackStatus
        )

        viewModel.postPaymentCallback(payment.id, callbackRequest)
    }
}