package com.rentmycar.rentmycar.controller

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.databinding.ModelCarAvailabilityTimeslotBinding
import com.rentmycar.rentmycar.databinding.ModelCarAvailabilityTitleBinding
import com.rentmycar.rentmycar.databinding.ModelCarDetailsTitleBinding
import com.rentmycar.rentmycar.databinding.ModelCarListItemHeaderBinding
import com.rentmycar.rentmycar.domain.model.Availability
import com.rentmycar.rentmycar.domain.model.Car
import com.rentmycar.rentmycar.domain.model.LocalException
import com.rentmycar.rentmycar.epoxy.EmptyListEpoxyModel
import com.rentmycar.rentmycar.epoxy.LoadingEpoxyModel
import com.rentmycar.rentmycar.epoxy.ViewBindingKotlinModel
import com.rentmycar.rentmycar.network.response.GetAvailabilityResponse
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class CarAvailabilityEpoxyController: PagedListEpoxyController<GetAvailabilityResponse>() {

    override fun buildItemModel(currentPosition: Int, item: GetAvailabilityResponse?): EpoxyModel<*> {
        return TimeslotGridItemEpoxyModel(
            productId = item!!.productId,
            status = item.status,
            startAt = item.startAt,
            endAt = item.endAt
        ).id(item.startAt)
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
            TimeslotGridTitleEpoxyModel(title = date).id("day").addTo(this)

            mapEntry.value.forEach { timeslot ->
                TimeslotGridItemEpoxyModel(timeslot.productId, timeslot.status, timeslot.startAt, timeslot.endAt).id("timeslot").addTo(this)
            }
        }
        super.addModels(models)
    }

    data class TimeslotGridTitleEpoxyModel(
        val title: String
    ): ViewBindingKotlinModel<ModelCarAvailabilityTitleBinding>(R.layout.model_car_availability_title) {

        override fun ModelCarAvailabilityTitleBinding.bind() {
            titleTextView.text = title
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }

    data class TimeslotGridItemEpoxyModel(
        val productId: Int?,
        val status: String,
        val startAt: String,
        val endAt: String
    ): ViewBindingKotlinModel<ModelCarAvailabilityTimeslotBinding>(R.layout.model_car_availability_timeslot) {
        override fun ModelCarAvailabilityTimeslotBinding.bind() {
            val formattedStartAt = convertDate(startAt)
            val formattedEndAt = convertDate(endAt)

            timeslotTextView.text = RentMyCarApplication.context.getString(R.string.timeslot_start_end, formattedStartAt, formattedEndAt)
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