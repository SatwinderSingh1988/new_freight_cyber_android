package com.app.freightCyber.presentation.home_dashboard.ewd.two_up.secondary_driver_auth

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentAddSecondaryDriverBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddSecondaryDriverFragment : BaseFragment<FragmentAddSecondaryDriverBinding>() {

    private val viewModel: AddSecondaryDriverFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_add_secondary_driver
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
                R.id.etFatigueManagement -> {
                    HelperUtils.showPopupMenu(requireContext() , binding.etFatigueManagement ,  listOf("Standard" ,"Basic Fatigue Management (BFM)" ,"Advanced Fatigue Management (AFM)" , "Exempt"))
                }
                R.id.tvNext -> {
                    HelperUtils.navigateWithSlideAnimations(findNavController() , R.id.twoUpConfirmDetailFragment)
                }
            }
        })
    }
}