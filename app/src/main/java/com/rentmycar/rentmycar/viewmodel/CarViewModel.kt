package com.rentmycar.rentmycar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentmycar.rentmycar.domain.model.Car
import com.rentmycar.rentmycar.domain.model.RentalPlan
import com.rentmycar.rentmycar.repository.CarRepository
import com.rentmycar.rentmycar.repository.RentalPlanRepository
import kotlinx.coroutines.launch

class CarViewModel: ViewModel() {

    private val carRepository = CarRepository()
    private val rentalPlanRepository = RentalPlanRepository()

    private val _rentalPlanByIdLiveData = MutableLiveData<RentalPlan?>()
    val rentalPlanByIdLiveData: LiveData<RentalPlan?> = _rentalPlanByIdLiveData

    private val _carListLiveData = MutableLiveData<List<Car?>>()
    val carListLiveData: LiveData<List<Car?>> = _carListLiveData

    private val _carByIdLiveData = MutableLiveData<Car?>()
    val carByIdLiveData: LiveData<Car?> = _carByIdLiveData

    fun getCarsList() {
        viewModelScope.launch {
            val response = carRepository.getCarList()
            _carListLiveData.postValue(response)
        }
    }

    fun getRentalPlanByCar(carId: Int) {
        viewModelScope.launch {
            val response = rentalPlanRepository.getRentalPlanByCar(carId)
            _rentalPlanByIdLiveData.postValue(response)
        }
    }

    fun getCarById(id: Int) {
        viewModelScope.launch {
            val response = carRepository.getCarById(id)
            _carByIdLiveData.postValue(response)
        }
    }

    fun getCarsByUser() {
        viewModelScope.launch {
            val response = carRepository.getCarsByUser()
            _carListLiveData.postValue(response)
        }
    }
}