package com.app.freightCyber.presentation.home_dashboard.job.report_incident

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.databinding.FragmentReportIncidentBinding
import com.app.freightCyber.databinding.ReportIncidentBottomSheetBinding
import com.app.freightCyber.core.base.bottom_sheet.BaseCustomBottomSheet
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ReportIncidentFragment : BaseFragment<FragmentReportIncidentBinding>() {

    private val viewModel: ReportIncidentFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_report_incident
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        checkBoxClickHandle()
        incidentReportBottomSheet()
    }


    private fun checkBoxClickHandle() {
        binding.cb.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                binding.innerCs.visibility = View.VISIBLE
            }else{
                binding.innerCs.visibility = View.GONE
            }
        }
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    findNavController().popBackStack()
                }
                R.id.tvNext -> {
                    HelperUtils.navigateWithSlideAnimations(findNavController() , R.id.jobSuccessFragment)
                }
                R.id.etType -> {
                    incidentTypeBottomSheet?.show()
                }
            }
        })
    }

    private var incidentTypeBottomSheet : BaseCustomBottomSheet<ReportIncidentBottomSheetBinding>? = null
    private fun incidentReportBottomSheet() {
        incidentTypeBottomSheet =
            BaseCustomBottomSheet(requireContext(),
                R.layout.report_incident_bottom_sheet,
                BaseCustomBottomSheet.Listener {
                    when (it.id) {
                        R.id.imgBack -> {
                            incidentTypeBottomSheet?.dismiss()
                        }
                    }
                })
        incidentTypeBottomSheet?.setCancelable(false)
    }

}