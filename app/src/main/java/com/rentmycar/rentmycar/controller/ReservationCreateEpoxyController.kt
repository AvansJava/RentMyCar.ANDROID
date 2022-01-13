package com.rentmycar.rentmycar.controller

import android.content.ContentProvider
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.content.ContentProviderCompat.requireContext
import com.airbnb.epoxy.EpoxyController
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.databinding.ModelReservationCreateButtonsBinding
import com.rentmycar.rentmycar.databinding.ModelReservationCreateDataPointBinding
import com.rentmycar.rentmycar.databinding.ModelReservationCreateHeaderBinding
import com.rentmycar.rentmycar.databinding.ModelReservationCreatePaymentMethodBinding
import com.rentmycar.rentmycar.domain.model.Availability
import com.rentmycar.rentmycar.domain.model.Reservation
import com.rentmycar.rentmycar.epoxy.LoadingEpoxyModel
import com.rentmycar.rentmycar.epoxy.ViewBindingKotlinModel
import kotlinx.android.synthetic.main.fragment_car_create.*
import kotlinx.android.synthetic.main.model_reservation_create_payment_method.*
import java.text.SimpleDateFormat
import java.util.*

class ReservationCreateEpoxyController(
    private val onBtnBackClicked: () -> Unit,
    private val onBtnPayClicked: (String) -> Unit,
    private val dropdownFieldBinding: (AutoCompleteTextView) -> Unit
): EpoxyController() {
    var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var reservation: Reservation? = null
        set(value) {
            field = value
            isLoading = false
            requestModelBuild()
        }

    var isDetailsView: Boolean = false
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        if (isLoading) {
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        ReservationHeaderEpoxyModel(reservation?.reservationNumber, reservation?.status)
            .id("header").addTo(this)

        ReservationDataPointEpoxyModel(
            RentMyCarApplication.context.getString(R.string.reservation_number),
            reservation?.reservationNumber,
            R.drawable.ic_baseline_access_time_24
        ).id("data_point_1").addTo(this)

        ReservationDataPointEpoxyModel(
            RentMyCarApplication.context.getString(R.string.price_product),
            RentMyCarApplication.context.getString(R.string.price_amount_reservation, reservation?.product?.price),
            R.drawable.ic_baseline_euro_symbol_24
        ).id("data_point_2").addTo(this)

        ReservationDataPointEpoxyModel(
            RentMyCarApplication.context.getString(R.string.price_reservation_insurance),
            RentMyCarApplication.context.getString(R.string.price_amount_reservation, reservation?.product?.insurancePrice),
            R.drawable.ic_baseline_euro_symbol_24
        ).id("data_point_3").addTo(this)

        ReservationDataPointEpoxyModel(
            RentMyCarApplication.context.getString(R.string.price_reservation),
            RentMyCarApplication.context.getString(R.string.price_amount_reservation, reservation?.price),
            R.drawable.ic_baseline_euro_symbol_24
            ).id("data_point_4").addTo(this)

        InsuranceDataPointEpoxyModel(
            RentMyCarApplication.context.getString(R.string.insurance_type),
            reservation?.product?.insuranceTypeId,
            true,
            R.drawable.ic_baseline_work_24
        ).id("data_point_5").addTo(this)

        InsuranceDataPointEpoxyModel(
            RentMyCarApplication.context.getString(R.string.description),
            reservation?.product?.insuranceTypeId,
            false,
            R.drawable.ic_baseline_work_24
        ).id("data_point_6").addTo(this)

        ReservationStatusEpoxyModel(
            RentMyCarApplication.context.getString(R.string.status),
            reservation?.status
        ).id("data_point_7").addTo(this)

        ReservationTimeDataPointEpoxyModel(
            RentMyCarApplication.context.getString(R.string.start_date),
            reservation?.availability?.get(0)?.startAt,
            R.drawable.ic_baseline_date_range_24
        ).id("data_point_8").addTo(this)

        ReservationTimeDataPointEpoxyModel(
            RentMyCarApplication.context.getString(R.string.end_date),
            reservation?.availability?.get(reservation?.availability!!.size - 1)?.endAt,
            R.drawable.ic_baseline_date_range_24
        ).id("data_point_9").addTo(this)

        if (isDetailsView && reservation?.paidAt != null) {
            ReservationTimeDataPointEpoxyModel(
                RentMyCarApplication.context.getString(R.string.paid_at),
                reservation?.paidAt,
                R.drawable.ic_baseline_check_circle_24
            ).id("data_point_10").addTo(this)
        }

        // If reservation status is not final show buttons and payment method dropdown
        if (!isDetailsView) {
            ReservationPaymentMethodEpoxyModel(dropdownFieldBinding).id("payment_method_selector").addTo(this)

            ReservationButtonsEpoxyModel(
                onBtnBackClicked,
                onBtnPayClicked,
                reservation?.reservationNumber
            ).id("buttons").addTo(this)
        }
    }

    data class ReservationHeaderEpoxyModel(
        val reservationNumber: String?,
        val status: String?
    ): ViewBindingKotlinModel<ModelReservationCreateHeaderBinding>(R.layout.model_reservation_create_header) {
        override fun ModelReservationCreateHeaderBinding.bind() {

            reservationHeaderTextView.text = reservationNumber

            when(status) {
                "COMPLETED" -> {
                    reservationImageView.setImageResource(R.drawable.ic_baseline_check_circle_24)
                }
                "PENDING" -> {
                    reservationImageView.setImageResource(R.drawable.ic_baseline_access_time_24)
                }
                "CANCELED" -> {
                    reservationImageView.setImageResource(R.drawable.ic_baseline_cancel_24)
                }
                "EXPIRED" -> {
                    reservationImageView.setImageResource(R.drawable.ic_baseline_cancel_24)
                }
            }
        }
    }

    data class ReservationDataPointEpoxyModel(
        val title: String?,
        val description: String?,
        val icon: Int
    ): ViewBindingKotlinModel<ModelReservationCreateDataPointBinding>(R.layout.model_reservation_create_data_point) {
        override fun ModelReservationCreateDataPointBinding.bind() {
            titleTextView.text = title
            descriptionTextView.text = description
            titleTextView.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
        }
    }

    data class InsuranceDataPointEpoxyModel(
        val title: String?,
        val description: String?,
        val isType: Boolean,
        val icon: Int
    ): ViewBindingKotlinModel<ModelReservationCreateDataPointBinding>(R.layout.model_reservation_create_data_point) {

        override fun ModelReservationCreateDataPointBinding.bind() {

            titleTextView.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
            titleTextView.text = title

            when(description) {
                "ALL_RISK" -> {
                    if (isType) {
                        descriptionTextView.text =
                                RentMyCarApplication.context.getString(R.string.title_all_risk)
                        } else {
                        descriptionTextView.text =
                            RentMyCarApplication.context.getString(R.string.description_all_risk)
                    }
                }
                "ALL_RISK_INTERNATIONAL" -> {
                    if (isType) {
                        descriptionTextView.text =
                            RentMyCarApplication.context.getString(R.string.title_all_risk_international)
                    } else {
                        descriptionTextView.text =
                            RentMyCarApplication.context.getString(R.string.description_all_risk_international)
                    }
                }
                "BASIC_COVERAGE" -> {
                    if (isType) {
                        descriptionTextView.text =
                            RentMyCarApplication.context.getString(R.string.title_basic_coverage)
                    } else {
                        descriptionTextView.text =
                            RentMyCarApplication.context.getString(R.string.description_basic_coverage)
                    }
                }
                "BASIC_COVERAGE_INTERNATIONAL" -> {
                    if (isType) {
                        descriptionTextView.text =
                            RentMyCarApplication.context.getString(R.string.title_basic_coverage_international)
                    } else {
                        descriptionTextView.text =
                            RentMyCarApplication.context.getString(R.string.description_basic_coverage_international)
                    }
                }
            }
        }
    }

    data class ReservationStatusEpoxyModel(
        val title: String?,
        val description: String?
    ): ViewBindingKotlinModel<ModelReservationCreateDataPointBinding>(R.layout.model_reservation_create_data_point) {

        override fun ModelReservationCreateDataPointBinding.bind() {

            titleTextView.text = title

            when(description) {
                "COMPLETED" -> {
                    descriptionTextView.text =
                    RentMyCarApplication.context.getString(R.string.status_completed)
                    titleTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_check_circle_24, 0, 0, 0)
                }
                "PENDING" -> {
                    descriptionTextView.text =
                    RentMyCarApplication.context.getString(R.string.status_pending)
                    titleTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_access_time_24, 0, 0, 0)
                }
                "CANCELED" -> {
                    descriptionTextView.text =
                    RentMyCarApplication.context.getString(R.string.status_canceled)
                    titleTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_cancel_24, 0, 0, 0)
                }
                "EXPIRED" -> {
                    descriptionTextView.text =
                    RentMyCarApplication.context.getString(R.string.status_expired)
                    titleTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_cancel_24, 0, 0, 0)
                }
            }
        }
    }

    class ReservationPaymentMethodEpoxyModel(
        val dropdownFieldBinding: (AutoCompleteTextView) -> Unit
    ):
        ViewBindingKotlinModel<ModelReservationCreatePaymentMethodBinding>(R.layout.model_reservation_create_payment_method) {
        override fun ModelReservationCreatePaymentMethodBinding.bind() {
            dropdownFieldBinding(autoCompleteTextView)
        }
    }

    class ReservationButtonsEpoxyModel(
        val onBtnBackClicked: () -> Unit,
        val onBtnPayClicked: (String) -> Unit,
        val reservationNumber: String?
    ): ViewBindingKotlinModel<ModelReservationCreateButtonsBinding>(R.layout.model_reservation_create_buttons) {
        override fun ModelReservationCreateButtonsBinding.bind() {

            btnBack.setOnClickListener {
                onBtnBackClicked()
            }

            btnContinue.setOnClickListener {
                if (reservationNumber != null) {
                    onBtnPayClicked(reservationNumber)
                }
            }
        }
    }

    data class ReservationTimeDataPointEpoxyModel(
        val title: String?,
        val description: String?,
        val icon: Int
    ): ViewBindingKotlinModel<ModelReservationCreateDataPointBinding>(R.layout.model_reservation_create_data_point) {
        override fun ModelReservationCreateDataPointBinding.bind() {
            titleTextView.text = title
            descriptionTextView.text = convertDate(description)
            titleTextView.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
        }

        private fun convertDate(input: String?): String? {
            if (input != null) {
                val date = SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss",
                    Locale.getDefault()
                ).parse(input)
                val format = SimpleDateFormat(
                    "HH:mm '('dd-MM-yyy')'",
                    Locale.getDefault()
                )
                return format.format(date!!)
            }
            return input
        }
    }
}