package com.app.freightCyber.presentation.home_dashboard.home

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.BR
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentHomeBinding
import com.app.freightCyber.databinding.JobRequestListItemBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.recycler_view.SimpleRecyclerViewAdapter
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment  : BaseFragment<FragmentHomeBinding>(){

    private val viewModel : HomeFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_home
    }

    override fun getViewModel(): BaseViewModel {
      return viewModel
    }

    override fun onCreateView(view: View) {
        setJobAdapter()
        initOnClick()
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.viewReport -> {
                   HelperUtils.navigateWithSlideAnimations(findNavController() , R.id.reportIssueFragment)
                }

                R.id.viewEwd -> {
                 findNavController().navigate(R.id.action_homeFragment_to_ewdFragment)
                }
                R.id.tvSeeAll -> {
                  findNavController().navigate(R.id.action_homeFragment_to_JobFragment)
                }
                R.id.csMyReport -> {
                  HelperUtils.navigateWithSlideAnimations(findNavController() , R.id.myReportFragment)
                }
            }
        })
    }


    private var jobAdapter : SimpleRecyclerViewAdapter<String, JobRequestListItemBinding>? = null
    @SuppressLint("NotifyDataSetChanged")
    private fun setJobAdapter() {
        jobAdapter =
            SimpleRecyclerViewAdapter(R.layout.job_request_list_item,
                BR.bean,
                SimpleRecyclerViewAdapter.SimpleCallback { v, m, pos ->
                    when (v.id) {
                        R.id.tvViewInfo -> {
                            findNavController().navigate(R.id.jobDetailsFragment)
                        }
                    }
                })
        binding.rvJobRequest.adapter = jobAdapter
        jobAdapter?.list = jobListData()
    }

    private fun jobListData() : ArrayList<String>{
        val list = ArrayList<String>()
        list.add("")
        return list
    }
}