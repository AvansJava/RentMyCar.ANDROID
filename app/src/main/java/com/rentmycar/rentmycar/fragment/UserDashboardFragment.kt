package com.rentmycar.rentmycar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.domain.model.Login
import com.rentmycar.rentmycar.viewmodel.UserViewModel

class UserDashboardFragment: Fragment() {

    private val viewModel: UserViewModel by lazy {
        ViewModelProvider(this)[UserViewModel::class.java]
    }
    private val safeArgs: UserDashboardFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val login = Login(
            email = safeArgs.email,
            password = safeArgs.password
        )
        viewModel.userLogin(login)
    }
}