package com.rentmycar.rentmycar.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.rentmycar.rentmycar.R
import com.rentmycar.rentmycar.RentMyCarApplication
import com.rentmycar.rentmycar.controller.RentalPlanListEpoxyController
import com.rentmycar.rentmycar.viewmodel.RentalPlanViewModel
import kotlinx.android.synthetic.main.fragment_rental_plan_list.*

class RentalPlanListFragment: Fragment() {

    private val epoxyController = RentalPlanListEpoxyController(::onRentalPlanSelected)
    private val viewModel: RentalPlanViewModel by lazy {
        ViewModelProvider(this)[RentalPlanViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rental_plan_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.rentalPlansListLiveData.observe(viewLifecycleOwner) { rentalPlans ->
            epoxyController.rentalPlans = rentalPlans
            if (rentalPlans == null) {
                Toast.makeText(requireActivity(), RentMyCarApplication.context.getString(R.string.network_call_failed), Toast.LENGTH_LONG).show()
                return@observe
            }
        }
        viewModel.getRentalPlans()

        rentalPlanAdd.setOnClickListener {
            val directions = RentalPlanListFragmentDirections.actionRentalPlanListFragmentToRentalPlanCreateFragment()
            findNavController().navigate(directions)
        }

        val epoxyRecyclerView = view.findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }

    private fun onRentalPlanSelected(id: Int) {
        val directions = RentalPlanListFragmentDirections.actionRentalPlanListFragmentToRentalPlanDetailsFragment(rentalPlanId = id)
        findNavController().navigate(directions)
    }
}