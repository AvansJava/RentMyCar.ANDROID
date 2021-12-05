package com.rentmycar.rentmycar

import android.app.Application
import android.content.Context

class RentMyCarApplication: Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}