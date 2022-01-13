package com.rentmycar.rentmycar.network.interceptor

import com.rentmycar.rentmycar.GlobalNavigator
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        // If request response is 401 logout user (redirect to login)
        val builder = chain.request().newBuilder()
        val response = chain.proceed(builder.build())

        if (response.code == 401) {
            GlobalNavigator.logout()
        }

        return response
    }
}