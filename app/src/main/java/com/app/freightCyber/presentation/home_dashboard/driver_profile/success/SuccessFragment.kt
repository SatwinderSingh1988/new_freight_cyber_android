package com.app.freightCyber.presentation.home_dashboard.driver_profile.success

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentSuccessBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuccessFragment : BaseFragment<FragmentSuccessBinding>() {

    private var data: String? = null
    private val viewModel: SuccessFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_success
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

                R.id.tvNext -> {
                    when (data) {
                        "FROM_COMPLIANCE" -> {
                            findNavController().popBackStack(R.id.complianceDataFragment , false)
                        }
                        else -> {
                            findNavController().popBackStack(R.id.helpFragment , true)
                        }
                    }
                }
            }
        })
    }
}