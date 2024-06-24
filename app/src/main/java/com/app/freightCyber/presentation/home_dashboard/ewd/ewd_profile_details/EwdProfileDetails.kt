package com.app.freightCyber.presentation.home_dashboard.ewd.ewd_profile_details

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentEwdProfileDetailsBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EwdProfileDetails : BaseFragment<FragmentEwdProfileDetailsBinding>() {

    private val viewModel: EwdProfileDetailsVM by viewModels()

    companion object {
        var from: String? = null
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_ewd_profile_details
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        if (from == "SOLO") {
            binding.tvSolo.text = "Solo"
        } else {
            binding.tvSolo.text = "2 UP"
        }
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    findNavController().popBackStack()
                }

                R.id.tvNext -> {
                    when (from) {
                        "SOLO" -> {
                            HelperUtils.navigateWithSlideAnimations(
                                findNavController(),
                                R.id.fatigueManagementFragment
                            )
                        }

                        else -> {
                            HelperUtils.navigateWithSlideAnimations(
                                findNavController(),
                                R.id.primaryDriverFatigueFragment
                            )
                        }
                    }
                }
            }
        })
    }
}