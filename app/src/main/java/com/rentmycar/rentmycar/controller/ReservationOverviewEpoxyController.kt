package com.rentmycar.rentmycar.controller

import com.airbnb.epoxy.EpoxyController
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.databinding.ModelReservationListItemBinding
import com.rentmycar.rentmycar.domain.model.Availability
import com.rentmycar.rentmycar.domain.model.LocalException
import com.rentmycar.rentmycar.domain.model.Reservation
import com.rentmycar.rentmycar.epoxy.EmptyListEpoxyModel
import com.rentmycar.rentmycar.epoxy.HeaderEpoxyModel
import com.rentmycar.rentmycar.epoxy.LoadingEpoxyModel
import com.rentmycar.rentmycar.epoxy.ViewBindingKotlinModel
import java.text.SimpleDateFormat
import java.util.*

class ReservationOverviewEpoxyController(
    private val onReservationSelected: (String, String) -> Unit
): EpoxyController() {
    var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var reservations: List<Reservation?> = emptyList()
        set(value) {
            field = value
            isLoading = false
            requestModelBuild()
        }

    override fun buildModels() {
        if (isLoading) {
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        HeaderEpoxyModel(
            RentMyCarApplication.context.getString(R.string.my_reservations)
        ).id("header").addTo(this)

        if (reservations.isEmpty()) {
            val localException = LocalException(
                RentMyCarApplication.context.getString(R.string.no_reservations_found),
                RentMyCarApplication.context.getString(R.string.no_reservations_available)
            )
            EmptyListEpoxyModel(localException).id("emptyList").addTo(this)
            return
        }

        reservations.forEach { reservation ->
            ReservationListItemEpoxyModel(
                reservation?.reservationNumber,
                reservation?.status,
                reservation?.availability,
                onReservationSelected
            ).id(reservation?.reservationNumber).addTo(this)
        }
    }

    data class ReservationListItemEpoxyModel(
        val reservationNumber: String?,
        val status: String?,
        val availability: List<Availability?>?,
        val onReservationSelected: (String, String) -> Unit
    ): ViewBindingKotlinModel<ModelReservationListItemBinding>(R.layout.model_reservation_list_item) {

        override fun ModelReservationListItemBinding.bind() {
            val startAt = convertDate(availability?.get(0)?.startAt!!)
            val endAt = convertDate(availability[availability.size - 1]?.endAt!!)

            reservationTextView.text = reservationNumber
            reservationItalicTextView.text =
                RentMyCarApplication.context.getString(R.string.reservation_list_time, startAt, endAt)

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

            root.setOnClickListener {
                onReservationSelected(reservationNumber!!, status!!)
            }
        }

        private fun convertDate(input: String): String {
            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",
                Locale.getDefault()).parse(input)
            val format = SimpleDateFormat(
                "HH:mm '('dd-MM-yyy')'",
                Locale.getDefault())
            return format.format(date!!)
        }
    }
}