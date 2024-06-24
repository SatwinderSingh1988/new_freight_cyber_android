package com.app.freightCyber.presentation.home_dashboard.driver_profile.dse.detail


import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentDseModuleDetailBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DSEModuleDetailFragment : BaseFragment<FragmentDseModuleDetailBinding>() {

    private val viewModel: DSEModuleDetailFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_dse_module_detail
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view : View) {
        initOnClick()
    }


    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                 findNavController().popBackStack()
                }

                R.id.tvNext -> {
                    HelperUtils.navigateWithSlideAnimations(findNavController() , R.id.congratulationFragment)
                }
            }
        })
    }
}