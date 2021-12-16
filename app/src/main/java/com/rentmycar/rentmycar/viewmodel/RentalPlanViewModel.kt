package com.rentmycar.rentmycar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentmycar.rentmycar.domain.model.RentalPlan
import com.rentmycar.rentmycar.repository.RentalPlanRepository
import kotlinx.coroutines.launch

class RentalPlanViewModel: ViewModel() {

    private val rentalPlanRepository = RentalPlanRepository()

    private val _rentalPlanByIdLiveData = MutableLiveData<RentalPlan?>()
    val rentalPlanByIdLiveData: LiveData<RentalPlan?> = _rentalPlanByIdLiveData

    private val _rentalPlanByCarLiveData = MutableLiveData<RentalPlan?>()
    val rentalPlanByCarLiveData: LiveData<RentalPlan?> = _rentalPlanByCarLiveData

    private val _deleteRentalPlanLiveData = MutableLiveData<String>()
    val deleteRentalPlanLiveData: LiveData<String?> = _deleteRentalPlanLiveData

    private val _rentalPlansListLiveData = MutableLiveData<List<RentalPlan?>>()
    val rentalPlansListLiveData: LiveData<List<RentalPlan?>> = _rentalPlansListLiveData

    fun getRentalPlanById(id: Int) {
        viewModelScope.launch {
            val response = rentalPlanRepository.getRentalPlanById(id)
            _rentalPlanByIdLiveData.postValue(response)
        }
    }

    fun getRentalPlanByCar(carId: Int) {
        viewModelScope.launch {
            val response = rentalPlanRepository.getRentalPlanByCar(carId)
            _rentalPlanByCarLiveData.postValue(response)
        }
    }

    fun deleteRentalPlan(id: Int) {
        viewModelScope.launch {
            val response = rentalPlanRepository.deleteRentalPlan(id)
            _deleteRentalPlanLiveData.postValue(response)
        }
    }

    fun postRentalPlan(rentalPlan: RentalPlan) {
        viewModelScope.launch {
            val response = rentalPlanRepository.postRentalPlan(rentalPlan)
            _rentalPlanByIdLiveData.postValue(response)
        }
    }

    fun getRentalPlans() {
        viewModelScope.launch {
            val response = rentalPlanRepository.getRentalPlans()
            _rentalPlansListLiveData.postValue(response)
        }
    }
}