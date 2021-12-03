package com.rentmycar.rentmycar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rentmycar.rentmycar.network.response.PostLoginResponse
import com.rentmycar.rentmycar.repository.UserRepository

class UserViewModel: ViewModel() {

    private val userRepository = UserRepository()

    private val _userLoginLiveData = MutableLiveData<PostLoginResponse?>()
    val userLoginLiveData: LiveData<PostLoginResponse?> = _userLoginLiveData


}