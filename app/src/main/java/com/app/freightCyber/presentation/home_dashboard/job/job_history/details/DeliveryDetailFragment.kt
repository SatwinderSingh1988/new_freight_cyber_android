package com.app.freightCyber.presentation.home_dashboard.job.job_history.details

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentDeliveryDetailBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DeliveryDetailFragment : BaseFragment<FragmentDeliveryDetailBinding>() {

    private val viewModel: DeliveryDetailFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_delivery_detail
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
                    findNavController().popBackStack()
                }

            }
        })
    }
}