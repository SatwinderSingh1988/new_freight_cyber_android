package com.app.freightCyber.presentation.home_dashboard.job.report.my_report.details

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentReportDelayDetailBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ReportDelayDetailsFragment : BaseFragment<FragmentReportDelayDetailBinding>() {

    private val viewModel: ReportDelayDetailsFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_report_delay_detail
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

                R.id.tvSubmit -> {
                 //   ImageUtils.navigateWithSlideAnimations(findNavController(), R.id.reportIncidentTwoFragment)
                }
            }
        })
    }
}