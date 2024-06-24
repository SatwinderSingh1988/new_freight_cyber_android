package com.app.freightCyber.presentation.home_dashboard.ewd.work_diary

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentWwdBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WWDFragment : BaseFragment<FragmentWwdBinding>() {

    private val viewModel: WWDFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_wwd
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
                R.id.tvNext -> {
                    findNavController().popBackStack(R.id.ewdFragment , false)
                }
            }
        })
    }
}