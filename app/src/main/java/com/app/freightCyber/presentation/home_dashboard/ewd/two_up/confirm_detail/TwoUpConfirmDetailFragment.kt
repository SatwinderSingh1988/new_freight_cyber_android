package com.app.freightCyber.presentation.home_dashboard.ewd.two_up.confirm_detail

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentTwoUpConfirmDetailBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TwoUpConfirmDetailFragment : BaseFragment<FragmentTwoUpConfirmDetailBinding>() {

    private val viewModel: TwoUpConfirmDetailFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_two_up_confirm_detail
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
                R.id.tvNext ->{
                    HelperUtils.navigateWithSlideAnimations(findNavController() , R.id.declarationTwoUpFragment)
                }
            }
        })
    }
}