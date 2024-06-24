package com.app.freightCyber.presentation.home_dashboard.compliance.ewd_system

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentEwdSystemBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EwdSystemFragment  : BaseFragment<FragmentEwdSystemBinding>(){

    private val viewModel : EwdSystemFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_ewd_system
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
