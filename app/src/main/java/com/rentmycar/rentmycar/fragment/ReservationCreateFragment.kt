package com.rentmycar.rentmycar.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.network.request.TimeslotIdRequest

class ReservationCreateFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reservation_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            val list = requireArguments().getParcelableArrayList<TimeslotIdRequest>("selectedTimeslots")
            Log.d("tag", requireArguments().toString())
        }

    }
}