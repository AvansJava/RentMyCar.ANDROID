package com.rentmycar.rentmycar.network.client

import com.rentmycar.rentmycar.domain.model.Login
import com.rentmycar.rentmycar.domain.model.Register
import com.rentmycar.rentmycar.network.response.GetUserResponse
import com.rentmycar.rentmycar.network.response.PostLoginResponse
import com.rentmycar.rentmycar.network.response.SimpleResponse
import com.rentmycar.rentmycar.network.service.UserService
import kotlinx.coroutines.delay
import retrofit2.Response

class UserClient(
    private val userService: UserService
): BaseClient() {

    suspend fun postUserLogin(login: Login): SimpleResponse<PostLoginResponse> {
        return safeApiCall { userService.postUserLogin(login) }
    }

    suspend fun postUserRegistration(register: Register): SimpleResponse<String> {
        return safeApiCall { userService.postUserRegistration(register) }
    }

    suspend fun confirmUser(token: String): SimpleResponse<String> {
        return safeApiCall { userService.getConfirmUser(token) }
    }

    suspend fun getUser(): SimpleResponse<GetUserResponse> {
        return safeApiCall { userService.getUser() }
    }
}