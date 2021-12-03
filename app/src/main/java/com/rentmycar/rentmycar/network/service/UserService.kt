package com.rentmycar.rentmycar.network.service

import com.rentmycar.rentmycar.domain.model.Login
import com.rentmycar.rentmycar.network.response.PostLoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("auth/login")
    suspend fun postUserLogin(@Body login: Login): Response<PostLoginResponse>
}