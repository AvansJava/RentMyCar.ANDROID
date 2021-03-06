package com.rentmycar.rentmycar.network.service

import com.rentmycar.rentmycar.domain.model.Login
import com.rentmycar.rentmycar.domain.model.Register
import com.rentmycar.rentmycar.domain.model.User
import com.rentmycar.rentmycar.network.response.GetUserResponse
import com.rentmycar.rentmycar.network.response.PostLoginResponse
import retrofit2.Response
import retrofit2.http.*

interface UserService {
    @POST("auth/login")
    suspend fun postUserLogin(@Body login: Login): Response<PostLoginResponse>

    @POST("auth/register/")
    suspend fun postUserRegistration(@Body register: Register): Response<String>

    @GET("auth/register/confirm/")
    suspend fun getConfirmUser(
        @Query("token") token: String
    ): Response<String>

    @GET("user/")
    suspend fun getUser(): Response<GetUserResponse>

    @PUT("user/")
    suspend fun putUserEdit(@Body user: User): Response<GetUserResponse>
}