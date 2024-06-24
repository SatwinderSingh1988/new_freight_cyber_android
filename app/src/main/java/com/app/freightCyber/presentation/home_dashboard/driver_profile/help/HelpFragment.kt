package com.app.freightCyber.presentation.home_dashboard.driver_profile.help

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentHelpBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HelpFragment : BaseFragment<FragmentHelpBinding>() {

    private var data: String? = null
    private val viewModel: HelpFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_help
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        data = arguments?.getString("FROM")

    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    findNavController().popBackStack()
                }

                R.id.tvSubmit -> {
                    when (data) {
                        "FROM_COMPLIANCE" -> {
                            val bundle = Bundle()
                            bundle.putString("FROM" , "FROM_COMPLIANCE")
                            HelperUtils.navigateWithSlideAnimations(findNavController(), R.id.successFragment , bundle)
                        }
                        else -> {
                            HelperUtils.navigateWithSlideAnimations(findNavController(), R.id.successFragment)
                        }
                    }

                }
            }
        })
    }
}