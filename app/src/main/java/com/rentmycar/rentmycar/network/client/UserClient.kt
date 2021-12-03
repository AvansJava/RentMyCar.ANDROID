package com.rentmycar.rentmycar.network.client

import com.rentmycar.rentmycar.domain.model.Login
import com.rentmycar.rentmycar.network.response.PostLoginResponse
import com.rentmycar.rentmycar.network.response.SimpleResponse
import com.rentmycar.rentmycar.network.service.UserService
import retrofit2.Response

class UserClient(
    private val userService: UserService
): BaseClient() {

    suspend fun postUserLogin(login: Login): SimpleResponse<PostLoginResponse> {
        return safeApiCall { userService.postUserLogin(login) }
    }
}