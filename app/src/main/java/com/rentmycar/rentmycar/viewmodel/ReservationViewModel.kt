package com.rentmycar.rentmycar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentmycar.rentmycar.domain.model.Availability
import com.rentmycar.rentmycar.domain.model.Reservation
import com.rentmycar.rentmycar.network.request.PostReservationRequest
import com.rentmycar.rentmycar.repository.ReservationRepository
import kotlinx.coroutines.launch

class ReservationViewModel: ViewModel() {
    private val reservationRepository = ReservationRepository()

    private val _reservationLiveData = MutableLiveData<Reservation?>()
    val reservationLiveData: LiveData<Reservation?> = _reservationLiveData

    private val _timeslotListLiveData = MutableLiveData<List<Availability>>()
    val timeslotListLiveData: LiveData<List<Availability>> = _timeslotListLiveData

    fun postReservation(reservation: PostReservationRequest) {
        viewModelScope.launch {
            val response = reservationRepository.postReservation(reservation)
            _reservationLiveData.postValue(response)
        }
    }

    fun getTimeslotsByReservation(reservationNumber: String) {
        viewModelScope.launch {
            val response = reservationRepository.getTimeslotsByReservation(reservationNumber)
            _timeslotListLiveData.postValue(response)
        }
    }

    fun getReservation(reservationNumber: String) {
        viewModelScope.launch {
            val response = reservationRepository.getReservation(reservationNumber)
            _reservationLiveData.postValue(response)
        }
    }
}