package com.rentmycar.rentmycar.network

import com.rentmycar.rentmycar.config.Config.BASE_URL
import com.rentmycar.rentmycar.network.client.*
import com.rentmycar.rentmycar.network.interceptor.AuthInterceptor
import com.rentmycar.rentmycar.network.interceptor.JwtInterceptor
import com.rentmycar.rentmycar.network.service.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkLayer {

    private val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    private val retrofit: Retrofit = Retrofit.Builder()
        .client(getLoggingHttpClient())
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    private val userService: UserService by lazy {
        retrofit.create(UserService::class.java)
    }

    private val carService: CarService by lazy {
        retrofit.create(CarService::class.java)
    }

    private val locationService: LocationService by lazy {
        retrofit.create(LocationService::class.java)
    }

    private val rentalPlanService: RentalPlanService by lazy {
        retrofit.create(RentalPlanService::class.java)
    }

    private val availabilityService: AvailabilityService by lazy {
        retrofit.create(AvailabilityService::class.java)
    }

    val userClient = UserClient(userService)
    val carClient = CarClient(carService)
    val locationClient = LocationClient(locationService)
    val rentalPlanClient = RentalPlanClient(rentalPlanService)
    val availabilityClient = AvailabilityClient(availabilityService)

    private fun getLoggingHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.addInterceptor(JwtInterceptor())
        client.addInterceptor(AuthInterceptor())
        client.addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })

        return client.build()
    }
}