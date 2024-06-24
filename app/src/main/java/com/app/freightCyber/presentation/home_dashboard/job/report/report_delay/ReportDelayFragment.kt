package com.app.freightCyber.presentation.home_dashboard.job.report.report_delay


import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentReportDelayBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ReportDelayFragment : BaseFragment<FragmentReportDelayBinding>() {

    private var amList: List<String>? = null
    private var mList: List<String>? = null
    private val viewModel: ReportDelayFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_report_delay
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        mList = listOf("item1" , "item2" , "item3")
        amList = listOf("AM" , "PM")

    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    findNavController().popBackStack()
                }
                R.id.etReportDate -> {
                    HelperUtils.openDatePickerDialog(requireContext() , binding.etReportDate)
                }
                R.id.tvSubmit -> {
                    HelperUtils.navigateWithSlideAnimations(findNavController(), R.id.reportSuccessFragment)
                }
                R.id.etDelayDate -> {
                    HelperUtils.openDatePickerDialog(requireContext() , binding.etDelayDate)
                }
            }
        })
    }
}