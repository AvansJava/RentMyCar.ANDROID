package com.rentmycar.rentmycar.network.interceptor

import com.rentmycar.rentmycar.GlobalNavigator
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val builder = chain.request().newBuilder()
        val response = chain.proceed(builder.build())

        if (response.code == 401) {
            GlobalNavigator.logout()
        }

        return response
    }
}