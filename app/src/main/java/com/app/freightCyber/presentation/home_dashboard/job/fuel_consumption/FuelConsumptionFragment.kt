package com.app.freightCyber.presentation.home_dashboard.job.fuel_consumption

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentFuelConsumptionBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FuelConsumptionFragment : BaseFragment<FragmentFuelConsumptionBinding>() {

    private val viewModel: FuelConsumptionFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_fuel_consumption
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view : View) {
        initOnClick()
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.imgBack ->{
                    findNavController().popBackStack()
                }
                R.id.tvNext->{
                    val bundle = Bundle()
                    bundle.putString("FROM" ,"FROM_FUEL")
                    HelperUtils.navigateWithSlideAnimations(findNavController() , R.id.inspectionCheckListTwoFragment , bundle)
                }
            }
        })
    }
}