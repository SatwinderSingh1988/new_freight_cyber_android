package com.app.freightCyber.presentation.home_dashboard.compliance.driver_details

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentSoloBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SoloFragment : BaseFragment<FragmentSoloBinding>() {

    private val viewModel: SoloFragmentVM  by viewModels()


    override fun getLayoutResource(): Int {
        return R.layout.fragment_solo
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                }
            }
        })
    }
}