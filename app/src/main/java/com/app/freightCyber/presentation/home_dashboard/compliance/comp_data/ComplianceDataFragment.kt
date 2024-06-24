package com.app.freightCyber.presentation.home_dashboard.compliance.comp_data

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.app.freightCyber.BR
import com.app.freightCyber.R
import com.app.freightCyber.domain.model.dummy_data.ComplianceData
import com.app.freightCyber.databinding.ComplianceListItemBinding
import com.app.freightCyber.databinding.FragmentComplianceDataBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.recycler_view.SimpleRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ComplianceDataFragment : BaseFragment<FragmentComplianceDataBinding>() {

    private val viewModel: ComplianceDataFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_compliance_data
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        setComplianceAdapter()
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


    private var authAdapter : SimpleRecyclerViewAdapter<ComplianceData, ComplianceListItemBinding>? = null
    @SuppressLint("NotifyDataSetChanged")
    private fun setComplianceAdapter() {
        authAdapter =
            SimpleRecyclerViewAdapter(R.layout.compliance_list_item,
                BR.bean,
                SimpleRecyclerViewAdapter.SimpleCallback { v, m, pos ->
                    when (m.authType) {
                        "Driver Details" -> {
                            // ImageUtils.navigateWithSlideAnimations(findNavController() ,R.id.loadingDataFragment)
                            findNavController().navigate(R.id.complianceDriverDetailFragment)
                        }

                        "EWD System" -> {
                            findNavController().navigate(R.id.ewdSystemFragment)
                        }

                        "Report Transfer" -> {
                            findNavController().navigate(R.id.reportTransferFragment)
                        }

                        "Annotations" -> {
                            findNavController().navigate(R.id.addAnnotationTwoFragment)
                        }

                        "Investigation\n" + "Aid" -> {
                            findNavController().navigate(R.id.investigationFragment)
                        }

                        "Work & Rest\n" + "Changes" -> {
                            findNavController().navigate(R.id.workRestChangesFragment)
                        }

                        "Work Diary\n" + "Summary" -> {
                            findNavController().navigate(R.id.workDiarySummaryFragment)
                        }

                        "Help" -> {
                            val bundle = Bundle()
                            bundle.putString("FROM", "FROM_COMPLIANCE")
                            findNavController().navigate(R.id.helpFragment, bundle)
                        }
                    }
                })
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvCompliance.layoutManager = layoutManager
        binding.rvCompliance.adapter = authAdapter
        authAdapter?.list = authList()
        layoutManager.spanSizeLookup = CenterLastItem(2 , authAdapter?.list?.size!!)
    }


    private fun authList() : ArrayList<ComplianceData>{
        val list = ArrayList<ComplianceData>()
        list.add(ComplianceData(R.drawable.work_diary ,"Work Diary\nSummary","Tap to set up fingerprint" ,false))
        list.add(ComplianceData(R.drawable.work_rest ,"Work & Rest\nChanges","Tap to set up face" , false))
        list.add(ComplianceData(R.drawable.img_search_2 ,"Investigation\nAid","Tap to set up voice" , false))
        list.add(ComplianceData(R.drawable.annotation ,"Annotations","Tap to set up voice" , false))
        list.add(ComplianceData(R.drawable.inbox ,"Report Transfer","Tap to set up fingerprint" ,false))
        list.add(ComplianceData(R.drawable.c_help ,"Help","Tap to set up face" , false))
        list.add(ComplianceData(R.drawable.driver_details ,"Driver Details","Tap to set up voice" , false))
        list.add(ComplianceData(R.drawable.ewd_system ,"EWD System","Tap to set up voice" , false))
        list.add(ComplianceData(R.drawable.operating ,"Device & Operating System","Tap to set up voice" , false))
        return list
    }
}
