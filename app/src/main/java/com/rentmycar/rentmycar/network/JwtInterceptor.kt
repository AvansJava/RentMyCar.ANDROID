package com.rentmycar.rentmycar.network

import android.util.Log
import com.rentmycar.rentmycar.AppPreference
import com.rentmycar.rentmycar.RentMyCarApplication
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class JwtInterceptor: Interceptor {

    private val preference = AppPreference(RentMyCarApplication.context)

    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        val requiresAuthentication: Boolean = !request.url.toString().contains("auth")
        val token = preference.getToken()
        if (token != null && requiresAuthentication) {
            request = request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        }

        return chain.proceed(request)
    }
}