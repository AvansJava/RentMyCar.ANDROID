package com.rentmycar.rentmycar.repository

import com.rentmycar.rentmycar.domain.model.Login
import com.rentmycar.rentmycar.network.NetworkLayer
import com.rentmycar.rentmycar.network.response.GetUserResponse
import com.rentmycar.rentmycar.network.response.PostLoginResponse

class UserRepository {

    suspend fun postUserLogin(login: Login): PostLoginResponse? {
        val request = NetworkLayer.userClient.postUserLogin(login)

        if (request.failed || !request.isSuccessful) {
            //todo error handling
            return null
        }

        return request.body
    }

    suspend fun getUser(): GetUserResponse? {
        val request = NetworkLayer.userClient.getUser()

        if (request.failed || !request.isSuccessful) {
            //todo error handling
            return null
        }

        return request.body
    }
}