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
import com.rentmycar.rentmycar.network.request.PutTimeslotRequest
import com.rentmycar.rentmycar.network.response.GetAvailabilityResponse
import com.rentmycar.rentmycar.network.response.GetTimeslotResponse
import com.rentmycar.rentmycar.repository.AvailabilityRepository
import kotlinx.coroutines.launch

class AvailabilityViewModel(
    carId: Int
): ViewModel() {

    private val availabilityRepository = AvailabilityRepository()
    private val pageListConfig: PagedList.Config = PagedList.Config.Builder()
        .setPageSize(DEFAULT_PAGE_SIZE)
        .setPrefetchDistance(PREFETCH_DISTANCE)
        .build()

    private val _defaultTimeslotsLiveData = MutableLiveData<List<GetTimeslotResponse?>>()
    val defaultTimeslotsLiveData: LiveData<List<GetTimeslotResponse?>> = _defaultTimeslotsLiveData

    private val _timeslotLiveData = MutableLiveData<GetAvailabilityResponse>()
    val timeslotLiveData: LiveData<GetAvailabilityResponse> = _timeslotLiveData

    private val dataSourceFactory = AvailabilityDataSourceFactory(viewModelScope, availabilityRepository, carId)
    val availabilityPagedListLiveData: LiveData<PagedList<GetAvailabilityResponse>> =
        LivePagedListBuilder(dataSourceFactory, pageListConfig).build()

    fun getDefaultTimeslots() {
        viewModelScope.launch {
            val response = availabilityRepository.getDefaultTimeslots()
            _defaultTimeslotsLiveData.postValue(response)
        }
    }

    fun updateTimeslotStatus(id: Int, status: PutTimeslotRequest) {
        viewModelScope.launch {
            val response = availabilityRepository.updateTimeslotStatus(id, status)
            _timeslotLiveData.postValue(response)
        }
    }
}

