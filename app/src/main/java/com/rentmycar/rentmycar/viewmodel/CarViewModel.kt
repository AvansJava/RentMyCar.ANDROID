package com.rentmycar.rentmycar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentmycar.rentmycar.domain.model.Car
import com.rentmycar.rentmycar.repository.CarRepository
import kotlinx.coroutines.launch

class CarViewModel: ViewModel() {

    private val carRepository = CarRepository()

    private val _carListLiveData = MutableLiveData<List<Car?>>()
    val carListLiveData: LiveData<List<Car?>> = _carListLiveData

    fun getCarsList() {
        viewModelScope.launch {
            val response = carRepository.getCarList()
            _carListLiveData.postValue(response)
        }
    }

}