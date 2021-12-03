package com.rentmycar.rentmycar.network.client

import com.rentmycar.rentmycar.network.response.SimpleResponse
import retrofit2.Response

abstract class BaseClient {

    inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }
}