package com.app.freightCyber.presentation.home_dashboard.ewd.start_rest

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentStartRestBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartRestFragment : BaseFragment<FragmentStartRestBinding>() {

    private val viewModel: StartRestFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_start_rest
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

                R.id.tvCancel -> {
                    findNavController().popBackStack()
                }
                R.id.etAMPM -> {
                  HelperUtils.showPopupMenu(requireContext() , binding.etAMPM , listOf("AM" , "PM"))
                }

                R.id.tvNext -> {
                    val bundle = Bundle()
                    bundle.putString("FROM" ,"CHANGE_STATUS_TO_REST")
                    HelperUtils.navigateWithSlideAnimations(findNavController() , R.id.dashboardFragment , bundle)
                }
            }
        })
    }
}