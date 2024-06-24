package com.app.freightCyber.presentation.home_dashboard.home

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentFullScreenImageBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowFullScreenImageFragment  : BaseFragment<FragmentFullScreenImageBinding>(){

    private val viewModel : ShowFullScreenImageFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_full_screen_image
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}