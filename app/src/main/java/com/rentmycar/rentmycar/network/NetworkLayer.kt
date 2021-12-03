package com.rentmycar.rentmycar.network

import com.rentmycar.rentmycar.config.Config.BASE_URL
import com.rentmycar.rentmycar.network.client.CarClient
import com.rentmycar.rentmycar.network.client.UserClient
import com.rentmycar.rentmycar.network.service.CarService
import com.rentmycar.rentmycar.network.service.UserService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkLayer {

    val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    val retrofit: Retrofit = Retrofit.Builder()
        .client(getLoggingHttpClient())
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val userService: UserService by lazy {
        retrofit.create(UserService::class.java)
    }

    val carService: CarService by lazy {
        retrofit.create(CarService::class.java)
    }

    val userClient = UserClient(userService)
    val carClient = CarClient(carService)

    private fun getLoggingHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })

        return client.build()
    }
}