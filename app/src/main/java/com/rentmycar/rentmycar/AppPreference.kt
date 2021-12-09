package com.rentmycar.rentmycar

import android.content.Context
import android.content.SharedPreferences

class AppPreference(context: Context) {

    val PREFERENCE_NAME = "SharedPreference"
    val PREFERENCE_TOKEN = "AccessToken"
    val PREFERENCE_FIRST_NAME = "firstName"
    val PREFERENCE_LAST_NAME = "lastName"
    val PREFERENCE_USER_ID  = "userId"

    private val preference: SharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getToken() : String? {
        return "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBnbWFpbC5jb20iLCJyb2xlcyI6WyJVU0VSIl0sImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9hcGkvdjEuMC9hdXRoL2xvZ2luIiwiZXhwIjoxNjM5MTMxMjU3fQ.oOyxWrN5Sgo2OCuGpALZj5xKTbUtXtxoZeG1S4CwIIM"
    }

    fun setToken(token: String) {
        preference.edit().putString(PREFERENCE_TOKEN, token).apply()
    }

    fun getFirstName() : String? {
        return preference.getString(PREFERENCE_FIRST_NAME, "")
    }

    fun setFirstName(firstName: String) {
        preference.edit().putString(PREFERENCE_FIRST_NAME, firstName).apply()
    }

    fun getLastName() : String? {
        return preference.getString(PREFERENCE_LAST_NAME, "")
    }

    fun setLastName(lastName: String) {
        preference.edit().putString(PREFERENCE_LAST_NAME, lastName).apply()
    }

    fun getUserId() : Int {
        return preference.getInt(PREFERENCE_USER_ID, 0)
    }

    fun setUserId(userId: Int) {
        preference.edit().putInt(PREFERENCE_USER_ID, userId).apply()
    }

    fun clearPreferences() {
        preference.edit().clear().apply()
    }
}