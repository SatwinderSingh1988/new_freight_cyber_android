package com.app.freightCyber.presentation.home_dashboard.ewd.ewd_fragment

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentEwdBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EwdFragment : BaseFragment<FragmentEwdBinding>() {

    private val viewModel: EwdFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_ewd
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        binding.circularProgress.setCurrentProgress(70.0)
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.viewStartWork ,  R.id.imgStartWork ,  R.id.tvStartWork -> {
                    HelperUtils.navigateWithSlideAnimations(findNavController() , R.id.driveArrangementFragment)
                }
                R.id.viewAddRest ,  R.id.imgRestWork ,  R.id.tvRestWork -> {
                    HelperUtils.navigateWithSlideAnimations(findNavController() , R.id.eventFragment)
                }
                R.id.viewSubmit ,  R.id.imgSubmit ,  R.id.tvSubmit -> {
                    HelperUtils.navigateWithSlideAnimations(findNavController() , R.id.submitDiaryFragment)
                }
                R.id.csProfile->{
                    HelperUtils.navigateWithSlideAnimations(findNavController() , R.id.workDiarySoloFragment)
                }
            }
        })
    }
}
