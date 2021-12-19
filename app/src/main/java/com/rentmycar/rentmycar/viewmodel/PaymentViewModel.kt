package com.rentmycar.rentmycar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentmycar.rentmycar.network.request.PostPaymentRequest
import com.rentmycar.rentmycar.network.response.PostPaymentResponse
import com.rentmycar.rentmycar.repository.PaymentRepository
import kotlinx.coroutines.launch

class PaymentViewModel: ViewModel() {

    private val paymentRepository = PaymentRepository()

    private val _paymentLiveData = MutableLiveData<PostPaymentResponse?>()
    val paymentLiveData: LiveData<PostPaymentResponse?> = _paymentLiveData

    fun postPayment(payment: PostPaymentRequest) {
        viewModelScope.launch {
            val response = paymentRepository.postPayment(payment)
            _paymentLiveData.postValue(response)
        }
    }
}