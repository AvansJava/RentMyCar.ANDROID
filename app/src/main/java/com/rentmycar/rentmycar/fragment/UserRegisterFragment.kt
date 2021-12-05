package com.rentmycar.rentmycar.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rentmycar.rentmycar.R
import kotlinx.android.synthetic.main.fragment_user_register.tvSignIn

class UserRegisterFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvSignIn.setOnClickListener {
            tvSignIn.setTextColor(Color.parseColor("#FF018786"))
            val directions = UserRegisterFragmentDirections.actionUserRegisterFragmentToUserLoginFragment()
            findNavController().navigate(directions)
        }
    }
}