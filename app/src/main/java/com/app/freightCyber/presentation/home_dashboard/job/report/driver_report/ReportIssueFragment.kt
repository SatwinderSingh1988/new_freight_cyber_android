package com.app.freightCyber.presentation.home_dashboard.job.report.driver_report

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.BR
import com.app.freightCyber.R
import com.app.freightCyber.domain.model.dummy_data.ReportData
import com.app.freightCyber.databinding.FragmentReportIssueBinding
import com.app.freightCyber.databinding.ReportListItemBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.recycler_view.SimpleRecyclerViewAdapter
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportIssueFragment : BaseFragment<FragmentReportIssueBinding>() {

    private val viewModel: ReportIssueFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_report_issue
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        setReportIssueAdapter()
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

    private var authAdapter : SimpleRecyclerViewAdapter<ReportData, ReportListItemBinding>? = null
    @SuppressLint("NotifyDataSetChanged")
    private fun setReportIssueAdapter() {
        authAdapter =
            SimpleRecyclerViewAdapter(R.layout.report_list_item,
                BR.bean,
                SimpleRecyclerViewAdapter.SimpleCallback { v, m, pos ->
                    when (m.reportText) {
                        "Report a delay" -> {
                            HelperUtils.navigateWithSlideAnimations(
                                findNavController(),
                                R.id.reportDelayFragment
                            )
                        }

                        "Report an Accident" -> {
                            HelperUtils.navigateWithSlideAnimations(
                                findNavController(),
                                R.id.reportIncidentTwoFragment
                            )
                        }

                        "Report cargo damage" -> {
                            HelperUtils.navigateWithSlideAnimations(
                                findNavController(),
                                R.id.reportDamageFragment
                            )
                        }
                    }
                })
        binding.rvReport.adapter = authAdapter
        authAdapter?.list = reportList()
    }


    private fun reportList() : ArrayList<ReportData>{
        val list = ArrayList<ReportData>()
        list.add(ReportData(R.drawable.delay ,"Report a delay"))
        list.add(ReportData(R.drawable.report_inci ,"Report an Accident"))
        list.add(ReportData(R.drawable.report_damag ,"Report cargo damage"))
        return list
    }
}