package com.rentmycar.rentmycar.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentmycar.rentmycar.domain.model.Car
import com.rentmycar.rentmycar.room.Car as CarRoom
import com.rentmycar.rentmycar.repository.CarRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class CarViewModel: ViewModel() {

    private val carRepository = CarRepository()

    private val _carListLiveData = MutableLiveData<List<Car?>>()
    val carListLiveData: LiveData<List<Car?>> = _carListLiveData

    private val _carByIdLiveData = MutableLiveData<Car?>()
    val carByIdLiveData: LiveData<Car?> = _carByIdLiveData

    private val _carResult = MutableLiveData<Int>()
    val carResult: LiveData<Int> get() = _carResult

    private val _carRoomLiveData = MutableLiveData<CarRoom?>()
    val carRoomLiveData: LiveData<CarRoom?> = _carRoomLiveData

    private val _carResourceResult = MutableLiveData<String>()
    val carResourceResult: LiveData<String> = _carResourceResult

    fun getCarsList() {
        viewModelScope.launch {
            val response = carRepository.getCarList()
            _carListLiveData.postValue(response)
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

    fun createCar(context: Context, car: CarRoom) {
        viewModelScope.launch {
            val response = carRepository.createCar(context, car)
            _carResult.value = response.toInt()
        }
    }

    fun updateCar(context: Context, locationId: Int, carId: Int) {
        viewModelScope.launch {
            val response = carRepository.updateCar(context, locationId, carId)
        }
    }

    fun getCar(context: Context, carId: Int) {
        viewModelScope.launch {
            val response = carRepository.getCar(context, carId)
            _carRoomLiveData.postValue(response)
        }
    }

    fun postCar(car: Car) {
        viewModelScope.launch {
            val response = carRepository.postCarWithLocation(car)
            _carByIdLiveData.postValue(response)
        }
    }

    fun putCar(id: Int, car: Car) {
        viewModelScope.launch {
            val response = carRepository.putCar(id, car)
            _carByIdLiveData.postValue(response)
        }
    }

    fun postCarResource(id: Int, image: MultipartBody.Part) {
        viewModelScope.launch {
            val response = carRepository.postCarResource(id, image)
            _carResourceResult.postValue(response)
        }
    }
}