package com.rentmycar.rentmycar.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rentmycar.rentmycar.domain.model.Location
import com.rentmycar.rentmycar.repository.LocationRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentmycar.rentmycar.room.Location as LocationRoom
import kotlinx.coroutines.launch

class LocationViewModel: ViewModel() {

    private val locationRepository = LocationRepository()

    private val _locationByIdLiveData = MutableLiveData<Location?>()
    val locationByIdLiveData: LiveData<Location?> = _locationByIdLiveData

    private val _locationsListLiveData = MutableLiveData<List<Location?>>()
    val locationsListLiveData: LiveData<List<Location?>> = _locationsListLiveData

    private val _deleteLocationLiveData = MutableLiveData<String>()
    val deleteLocationLiveData: LiveData<String> = _deleteLocationLiveData

    private val _locationResult = MutableLiveData<Int>()
    val locationResult: LiveData<Int> get() = _locationResult

    fun getLocationById(id: Int) {
        viewModelScope.launch {
            val response = locationRepository.getLocationById(id)
            _locationByIdLiveData.postValue(response)
        }
    }

    fun postLocation(location: Location) {
        viewModelScope.launch {
            val response = locationRepository.postLocation(location)
            _locationByIdLiveData.postValue(response)
        }
    }

    fun getLocations() {
        viewModelScope.launch {
            val response = locationRepository.getLocations()
            _locationsListLiveData.postValue(response)
        }
    }

    fun updateLocation(id: Int, location: Location) {
        viewModelScope.launch {
            val response = locationRepository.updateLocation(id, location)
            _locationByIdLiveData.postValue(response)
        }
    }

    fun deleteLocation(id: Int) {
        viewModelScope.launch {
            val response = locationRepository.deleteLocation(id)
            _deleteLocationLiveData.postValue(response)
        }
    }

    fun createLocation(context: Context, location: LocationRoom) {
        viewModelScope.launch {
            val response = locationRepository.createLocation(context, location)
            _locationResult.value = response.toInt()
        }
    }
}