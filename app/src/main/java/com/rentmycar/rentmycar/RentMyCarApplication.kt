package com.rentmycar.rentmycar

import android.app.Application
import android.content.Context

class RentMyCarApplication: Application() {
    companion object {
        lateinit var context: Context
    }

    // define application context
    override fun onCreate() {
        super.onCreate()
        context = this
    }
}