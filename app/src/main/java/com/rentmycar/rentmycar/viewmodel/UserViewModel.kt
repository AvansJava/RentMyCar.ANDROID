package com.rentmycar.rentmycar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentmycar.rentmycar.domain.model.Login
import com.rentmycar.rentmycar.network.response.GetUserResponse
import com.rentmycar.rentmycar.network.response.PostLoginResponse
import com.rentmycar.rentmycar.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {

    private val userRepository = UserRepository()

    private val _userLoginLiveData = MutableLiveData<PostLoginResponse?>()
    val userLoginLiveData: LiveData<PostLoginResponse?> = _userLoginLiveData

    private val _getUserLiveData = MutableLiveData<GetUserResponse?>()
    val getUserLiveData: LiveData<GetUserResponse?> = _getUserLiveData

    fun userLogin(login: Login) {
        viewModelScope.launch {
            val response = userRepository.postUserLogin(login)
            _userLoginLiveData.postValue(response)
        }
    }

    fun getUser() {
        viewModelScope.launch {
            val response = userRepository.getUser()
            _getUserLiveData.postValue(response)
        }
    }
}