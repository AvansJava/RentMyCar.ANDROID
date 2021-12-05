package com.rentmycar.rentmycar.network

import android.content.Context
import android.content.SharedPreferences
import com.rentmycar.rentmycar.RentMyCarApplication.Companion.context
import com.rentmycar.rentmycar.config.Config.ACCESS_TOKEN
import com.rentmycar.rentmycar.config.Config.PREFERENCES
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class JwtInterceptor: Interceptor {
    private val sp: SharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
    private val token = sp.getString(ACCESS_TOKEN, null)

    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        if (token != null) {
            request = request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        }
        return chain.proceed(request)
    }
}