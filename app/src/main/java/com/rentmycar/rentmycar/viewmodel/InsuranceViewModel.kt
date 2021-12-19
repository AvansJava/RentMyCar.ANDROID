package com.rentmycar.rentmycar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentmycar.rentmycar.domain.model.InsuranceType
import com.rentmycar.rentmycar.repository.InsuranceRepository
import kotlinx.coroutines.launch

class InsuranceViewModel: ViewModel() {

    private val insuranceRepository = InsuranceRepository()

    private val _insuranceTypeListLiveData = MutableLiveData<List<InsuranceType?>>()
    val insuranceTypeListLiveData: LiveData<List<InsuranceType?>> = _insuranceTypeListLiveData

    fun getInsuranceTypes() {
        viewModelScope.launch {
            val response = insuranceRepository.getInsuranceTypes()
            _insuranceTypeListLiveData.postValue(response)
        }
    }
}