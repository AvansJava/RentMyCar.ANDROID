package com.rentmycar.rentmycar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent




class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnRegister.setOnClickListener {
            setContentView(R.layout.activity_register)
        }

        tvSignIn.setOnClickListener {
            setContentView(R.layout.activity_login)
        }
    }
}
