package com.app.freightCyber.presentation.home_dashboard.job.job_history.accepted

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.BR
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentAccepetdBinding
import com.app.freightCyber.databinding.JobRequestListItemBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.recycler_view.SimpleRecyclerViewAdapter
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AcceptedFragment : BaseFragment<FragmentAccepetdBinding>() {

    private val viewModel: AcceptedFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_accepetd
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view : View) {
        initOnClick()
        setAcceptJobAdapter()
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.imgBack ->{
                    findNavController().popBackStack()
                }

            }
        })
    }

    private var jobAdapter : SimpleRecyclerViewAdapter<String, JobRequestListItemBinding>? = null
    @SuppressLint("NotifyDataSetChanged")
    private fun setAcceptJobAdapter() {
        jobAdapter =
            SimpleRecyclerViewAdapter(
                R.layout.job_request_list_item, BR.bean,
                SimpleRecyclerViewAdapter.SimpleCallback { v, m, pos ->
                    when (v.id) {
                        R.id.tvViewInfo -> {
                            HelperUtils.navigateWithSlideAnimations(
                                findNavController(),
                                R.id.jobHistoryDetailFragment
                            )
                        }
                    }
                })
        binding.rvAccepted.adapter = jobAdapter
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