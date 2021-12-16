package com.rentmycar.rentmycar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentmycar.rentmycar.network.response.GetTimeslotResponse
import com.rentmycar.rentmycar.repository.AvailabilityRepository
import kotlinx.coroutines.launch

class AvailabilityViewModel: ViewModel() {

    private val availabilityRepository = AvailabilityRepository()

    private val _defaultTimeslotsLiveData = MutableLiveData<List<GetTimeslotResponse?>>()
    val defaultTimeslotsLiveData: LiveData<List<GetTimeslotResponse?>> = _defaultTimeslotsLiveData

    fun getDefaultTimeslots() {
        viewModelScope.launch {
            val response = availabilityRepository.getDefaultTimeslots()
            _defaultTimeslotsLiveData.postValue(response)
        }
    }
}