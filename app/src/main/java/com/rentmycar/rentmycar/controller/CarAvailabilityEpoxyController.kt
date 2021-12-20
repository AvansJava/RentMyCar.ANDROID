package com.rentmycar.rentmycar.controller

import android.os.Build
import androidx.annotation.RequiresApi
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.databinding.*
import com.rentmycar.rentmycar.epoxy.LoadingEpoxyModel
import com.rentmycar.rentmycar.epoxy.ViewBindingKotlinModel
import com.rentmycar.rentmycar.network.response.GetAvailabilityResponse
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class CarAvailabilityEpoxyController(
    private val timeslotSelected: (Int) -> Unit
): PagedListEpoxyController<GetAvailabilityResponse>() {

    override fun buildItemModel(currentPosition: Int, item: GetAvailabilityResponse?): EpoxyModel<*> {
        return TimeslotGridItemEpoxyModel(
            id = item!!.id,
            rentalPlanId = item.rentalPlanId,
            productId = item.productId,
            status = item.status,
            startAt = item.startAt,
            endAt = item.endAt,
            timeslotSelected = timeslotSelected
        ).id(item.id)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun addModels(models: List<EpoxyModel<*>>) {
        if (models.isEmpty()) {
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        (models as List<TimeslotGridItemEpoxyModel>).groupBy {
            it.startAt.split("T")[0]
        }.forEach { mapEntry ->
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val date = LocalDate.parse(mapEntry.key, formatter).toString()
            TimeslotGridTitleEpoxyModel(title = date).id("day_${mapEntry.key}").addTo(this)

            mapEntry.value.forEach { timeslot ->
                TimeslotGridItemEpoxyModel(
                    timeslot.id,
                    timeslot.rentalPlanId,
                    timeslot.productId,
                    timeslot.status,
                    timeslot.startAt,
                    timeslot.endAt,
                    timeslot.timeslotSelected
                ).id("timeslot_${timeslot.id}").addTo(this)
            }
        }
    }

    data class TimeslotGridTitleEpoxyModel(
        val title: String
    ): ViewBindingKotlinModel<ModelCarAvailabilityTitleBinding>(R.layout.model_car_availability_title) {

        @RequiresApi(Build.VERSION_CODES.O)
        override fun ModelCarAvailabilityTitleBinding.bind() {
            titleTextView.text = convertDate(title)
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }

        @RequiresApi(Build.VERSION_CODES.O)
        private fun convertDate(input: String): String {
            val date = LocalDate.parse(input)
            return RentMyCarApplication.context.getString(R.string.date_calendar, date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)))
        }
    }

    data class TimeslotGridItemEpoxyModel(
        val id: Int,
        val rentalPlanId: Int,
        val productId: Int?,
        val status: String,
        val startAt: String,
        val endAt: String,
        val timeslotSelected: (Int) -> Unit
    ): ViewBindingKotlinModel<ModelCarAvailabilityTimeslotBinding>(R.layout.model_car_availability_timeslot) {
        @RequiresApi(Build.VERSION_CODES.M)
        override fun ModelCarAvailabilityTimeslotBinding.bind() {
            val formattedStartAt = convertDate(startAt)
            val formattedEndAt = convertDate(endAt)
            timeslotCheckbox.text = RentMyCarApplication.context.getString(R.string.timeslot_start_end, formattedStartAt, formattedEndAt)

            if (productId != null || status == "CLOSED") {
                timeslotCard.setCardBackgroundColor(RentMyCarApplication.context.getColor(R.color.light_red))
                timeslotCheckbox.isEnabled = false
                timeslotCheckbox.isChecked = false
            } else {
                timeslotCard.setCardBackgroundColor(RentMyCarApplication.context.getColor(R.color.light_green))
                timeslotCheckbox.isEnabled = true
                timeslotCheckbox.isChecked = false
            }

            timeslotCheckbox.setOnClickListener {
                timeslotSelected(id)
            }
        }

        private fun convertDate(input: String): String {
            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",
                Locale.getDefault()).parse(input)
            val format = SimpleDateFormat(
                "HH:mm",
                Locale.getDefault())
            return format.format(date!!)
        }
    }
}