package com.rentmycar.rentmycar

import android.content.Context
import android.content.SharedPreferences

class AppPreference(context: Context) {

    val PREFERENCE_NAME = "SharedPreference"
    val PREFERENCE_TOKEN = "AccessToken"

    private val preference: SharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getToken() : String? {
        return preference.getString(PREFERENCE_TOKEN, "")
    }

    fun setToken(token: String) {
        preference.edit().putString(PREFERENCE_TOKEN, token).apply()
    }
}