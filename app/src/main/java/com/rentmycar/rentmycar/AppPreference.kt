package com.rentmycar.rentmycar

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext

class AppPreference(context: Context) {

    val PREFERENCE_NAME = "SharedPreference"
    val PREFERENCE_TOKEN = "AccessToken"
    val PREFERENCE_FIRST_NAME = "firstName"
    val PREFERENCE_LAST_NAME = "lastName"
    val PREFERENCE_USER_ID  = "userId"

    private val preference: SharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getToken() : String? {
        return preference.getString(PREFERENCE_TOKEN, "token")
    }

    fun setToken(token: String?) {
        preference.edit().putString(PREFERENCE_TOKEN, token).apply()
    }

    fun getFirstName() : String? {
        return preference.getString(PREFERENCE_FIRST_NAME, "")
    }

    fun setFirstName(firstName: String?) {
        preference.edit().putString(PREFERENCE_FIRST_NAME, firstName).apply()
    }

    fun getLastName() : String? {
        return preference.getString(PREFERENCE_LAST_NAME, "")
    }

    fun setLastName(lastName: String?) {
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