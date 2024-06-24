package com.app.freightCyber.presentation.home_dashboard.compliance.add_annotation

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentAddAnnotationsBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddAnnotationFragment : BaseFragment<FragmentAddAnnotationsBinding>() {

    private val viewModel: AddAnnotationFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_add_annotations
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

                R.id.etDate -> {
                  HelperUtils.openDatePickerDialog(requireContext() , binding.etDate)
                }

                R.id.tvNext -> {
                    findNavController().popBackStack()
                }

                R.id.etTimeOfIntercept -> {
                  HelperUtils.openTimePickerDialog(requireContext() , binding.etTimeOfIntercept)
                }
            }
        })
    }
}