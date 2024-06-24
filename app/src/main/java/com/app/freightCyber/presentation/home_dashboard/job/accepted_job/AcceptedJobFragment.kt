package com.app.freightCyber.presentation.home_dashboard.job.accepted_job

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.BR
import com.app.freightCyber.databinding.FragmentAcceptedJobBinding
import com.app.freightCyber.databinding.JobRequestListItemBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.recycler_view.SimpleRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AcceptedJobFragment  : BaseFragment<FragmentAcceptedJobBinding>(){

    private val viewModel : AcceptedJobFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_accepted_job
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
          setJobAdapter()
    }

     private var jobAdapter : SimpleRecyclerViewAdapter<String, JobRequestListItemBinding>? = null
     @SuppressLint("NotifyDataSetChanged")
     private fun setJobAdapter() {
         jobAdapter =
             SimpleRecyclerViewAdapter(
                 R.layout.job_request_list_item, BR.bean,
                 SimpleRecyclerViewAdapter.SimpleCallback { v, m, pos ->
                     when (v.id) {
                         R.id.tvViewInfo -> {
                             findNavController().navigate(R.id.jobDetailsFragment)
                         }
                     }
                 })
         binding.rvAddedBusiness.adapter = jobAdapter
         jobAdapter?.list = jobListData()
     }

    private fun jobListData() : ArrayList<String>{
        val list = ArrayList<String>()
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        return list
    }
}