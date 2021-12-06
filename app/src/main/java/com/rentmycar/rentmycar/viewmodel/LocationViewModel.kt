package com.rentmycar.rentmycar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rentmycar.rentmycar.domain.model.Location
import com.rentmycar.rentmycar.repository.LocationRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LocationViewModel: ViewModel() {

    private val locationRepository = LocationRepository()

    private val _locationByIdLiveData = MutableLiveData<Location?>()
    val locationByIdLiveData: LiveData<Location?> = _locationByIdLiveData

    fun getLocationById(id: Int) {
        viewModelScope.launch {
            val response = locationRepository.getLocationById(id)
            _locationByIdLiveData.postValue(response)
        }
    }
}