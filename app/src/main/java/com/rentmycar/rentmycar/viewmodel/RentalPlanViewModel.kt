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
    val locationByIdLiveData: LiveData<RentalPlan?> = _rentalPlanByIdLiveData

    fun getRentalPlanById(id: Int) {
        viewModelScope.launch {
            val response = rentalPlanRepository.getRentalPlanById(id)
            _rentalPlanByIdLiveData.postValue(response)
        }
    }
}