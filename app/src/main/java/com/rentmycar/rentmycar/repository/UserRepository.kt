package com.rentmycar.rentmycar.repository

import android.util.Log
import android.widget.Toast
import com.rentmycar.rentmycar.AppPreference
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.domain.model.Login
import com.rentmycar.rentmycar.domain.model.Register
import com.rentmycar.rentmycar.network.NetworkLayer
import com.rentmycar.rentmycar.network.response.GetUserResponse
import com.rentmycar.rentmycar.network.response.PostLoginResponse

class UserRepository {

    private val preference = AppPreference(RentMyCarApplication.context)

    suspend fun postUserLogin(login: Login): PostLoginResponse? {
        val request = NetworkLayer.userClient.postUserLogin(login)

        if (request.failed || !request.isSuccessful) {
            if (request.data?.code() == 401) {
                Toast.makeText(RentMyCarApplication.context, RentMyCarApplication.context.getString(R.string.unauthorized), Toast.LENGTH_LONG).show()
            }
            return null
        }

        preference.clearPreferences()
        preference.setToken(request.body.accessToken)
        preference.setUserId(request.body.userId.toInt())
        preference.setFirstName(request.body.firstName)
        preference.setLastName(request.body.lastName)

        return request.body
    }

    suspend fun postUserRegistration(register: Register): String? {
        val request = NetworkLayer.userClient.postUserRegistration(register)

        if (request.failed || !request.isSuccessful) {
            Toast.makeText(RentMyCarApplication.context, RentMyCarApplication.context.getString(R.string.registration_failed), Toast.LENGTH_LONG).show()
            return null
        }

        return request.body
    }

    suspend fun confirmUser(token: String): String {
        val request = NetworkLayer.userClient.confirmUser(token)

        if (request.failed || !request.isSuccessful) {
            return request.toString()
        }

        return request.toString()
    }

    suspend fun getUser(): GetUserResponse? {
        val request = NetworkLayer.userClient.getUser()

        if (request.failed || !request.isSuccessful) {
            Toast.makeText(RentMyCarApplication.context, RentMyCarApplication.context.getString(R.string.error_get_user), Toast.LENGTH_LONG).show()
            return null
        }

        return request.body
    }
}