package com.app.freightCyber.presentation.home_dashboard.compliance.work_diary_summary

import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentWorkDiarySummaryBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WorkDiarySummaryFragment : BaseFragment<FragmentWorkDiarySummaryBinding>() {

    private val viewModel: WorkDiarySummaryFragmentVM by viewModels()


    override fun getLayoutResource(): Int {
        return R.layout.fragment_work_diary_summary
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        binding.progressWork.setCurrentProgress(70.0)
        binding.progressWork.progressColor = ContextCompat.getColor(requireContext() , R.color.green_progress)
        binding.progressRest.setCurrentProgress(70.0)
        binding.progressRest.progressColor = ContextCompat.getColor(requireContext() , R.color.yellow2)
        binding.progrssVoiltion.setCurrentProgress(70.0)
        binding.progrssVoiltion.progressColor = ContextCompat.getColor(requireContext() , R.color.pink)
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                  findNavController().popBackStack()
                }
            }
        })
    }
}