package com.rentmycar.rentmycar.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class JwtInterceptor(
    private val token: String?
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        val requiresAuthentication: Boolean = !request.url.toString().contains("auth")
        if (token != null && requiresAuthentication) {
            request = request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        }
        return chain.proceed(request)
    }
}