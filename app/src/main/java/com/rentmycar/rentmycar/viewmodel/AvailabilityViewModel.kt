package com.rentmycar.rentmycar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.rentmycar.rentmycar.config.Config.DEFAULT_PAGE_SIZE
import com.rentmycar.rentmycar.config.Config.PREFETCH_DISTANCE
import com.rentmycar.rentmycar.domain.AvailabilityDataSourceFactory
import com.rentmycar.rentmycar.domain.model.Availability
import com.rentmycar.rentmycar.network.response.GetAvailabilityResponse
import com.rentmycar.rentmycar.network.response.GetTimeslotResponse
import com.rentmycar.rentmycar.repository.AvailabilityRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AvailabilityViewModel(
    private val carId: Int
): ViewModel() {

    private val availabilityRepository = AvailabilityRepository()
    private val pageListConfig: PagedList.Config = PagedList.Config.Builder()
        .setPageSize(DEFAULT_PAGE_SIZE)
        .setPrefetchDistance(PREFETCH_DISTANCE)
        .build()

    private val _defaultTimeslotsLiveData = MutableLiveData<List<GetTimeslotResponse?>>()
    val defaultTimeslotsLiveData: LiveData<List<GetTimeslotResponse?>> = _defaultTimeslotsLiveData

    private val dataSourceFactory = AvailabilityDataSourceFactory(viewModelScope, availabilityRepository, carId)
    val availabilityPagedListLiveData: LiveData<PagedList<GetAvailabilityResponse>> =
        LivePagedListBuilder(dataSourceFactory, pageListConfig).build()

    fun getDefaultTimeslots() {
        viewModelScope.launch {
            val response = availabilityRepository.getDefaultTimeslots()
            _defaultTimeslotsLiveData.postValue(response)
        }
    }
}

