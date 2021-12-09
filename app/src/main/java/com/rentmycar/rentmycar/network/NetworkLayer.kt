package com.rentmycar.rentmycar.network

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.ContentProviderCompat.requireContext
import com.rentmycar.rentmycar.AppPreference
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.config.Config
import com.rentmycar.rentmycar.config.Config.ACCESS_TOKEN
import com.rentmycar.rentmycar.config.Config.BASE_URL
import com.rentmycar.rentmycar.network.client.CarClient
import com.rentmycar.rentmycar.network.client.LocationClient
import com.rentmycar.rentmycar.network.client.RentalPlanClient
import com.rentmycar.rentmycar.network.client.UserClient
import com.rentmycar.rentmycar.network.service.CarService
import com.rentmycar.rentmycar.network.service.LocationService
import com.rentmycar.rentmycar.network.service.RentalPlanService
import com.rentmycar.rentmycar.network.service.UserService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkLayer {

    private val preference = AppPreference(RentMyCarApplication.context)
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

    val userClient = UserClient(userService)
    val carClient = CarClient(carService)
    val locationClient = LocationClient(locationService)
    val rentalPlantClient = RentalPlanClient(rentalPlanService)

    private fun getLoggingHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.addInterceptor(JwtInterceptor(preference.getToken()))
        client.addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })

        return client.build()
    }
}