package com.rentmycar.rentmycar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentmycar.rentmycar.domain.model.Login
import com.rentmycar.rentmycar.domain.model.Register
import com.rentmycar.rentmycar.domain.model.User
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

    private val _userRegisterLiveData = MutableLiveData<String>()
    val userRegisterLiveData: LiveData<String> = _userRegisterLiveData

    private val _userConfirmationLiveData = MutableLiveData<String>()
    val userConfirmationLiveData: LiveData<String> = _userConfirmationLiveData

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

    fun putUser(user: User) {
        viewModelScope.launch {
            val response = userRepository.putUser(user)
            _getUserLiveData.postValue(response)
        }
    }

    fun registerUser(register: Register) {
        viewModelScope.launch {
            val response = userRepository.postUserRegistration(register)
            _userRegisterLiveData.postValue(response)
        }
    }

    fun confirmUser(token: String) {
        viewModelScope.launch {
            val response = userRepository.confirmUser(token)
            _userConfirmationLiveData.postValue(response)
        }
    }
}