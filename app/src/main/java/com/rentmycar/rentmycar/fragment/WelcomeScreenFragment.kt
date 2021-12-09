package com.rentmycar.rentmycar.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rentmycar.rentmycar.R
import kotlinx.android.synthetic.main.fragment_welcome_screen.*

class WelcomeScreenFragment: Fragment() {

    lateinit var button: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_welcome_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        btnRegister.setOnClickListener {
            val directions = WelcomeScreenFragmentDirections.actionWelcomeScreenFragmentToUserRegisterFragment()
            findNavController().navigate(directions)
        }

        tvSignIn.setOnClickListener {
            tvSignIn.setTextColor(Color.parseColor("#FF018786"))
            val directions = WelcomeScreenFragmentDirections.actionWelcomeScreenFragmentToUserLoginFragment()
            findNavController().navigate(directions)
        }
    }
}