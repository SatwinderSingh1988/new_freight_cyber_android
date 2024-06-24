package com.app.freightCyber.presentation.home_dashboard.ewd.switch_driver_biomatrics

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentSwitchDriverBioBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SwitchDriverBioFragment : BaseFragment<FragmentSwitchDriverBioBinding>() {

    private val viewModel: SwitchDriverBioFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_switch_driver_bio
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
                R.id.container , R.id.imgFingerprint , R.id.tvAuthType -> {
                    val bundle = Bundle()
                    bundle.putString("FROM" ,"SWITCH_DRIVER_BIO")
                    findNavController().navigate(R.id.twoUpDashboardFragment , bundle)
                }
            }
        })
    }
}