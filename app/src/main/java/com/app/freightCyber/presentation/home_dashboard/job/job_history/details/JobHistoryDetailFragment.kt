package com.app.freightCyber.presentation.home_dashboard.job.job_history.details

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentJobHistoryDetailBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class JobHistoryDetailFragment  : BaseFragment<FragmentJobHistoryDetailBinding>(){

    private val viewModel : JobHistoryDetailFragmentVM by viewModels()

    private val jobsArray = arrayOf(
        "Pickup Details",
        "Delivery Details"
    )

    override fun getLayoutResource(): Int {
        return R.layout.fragment_job_history_detail
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initViewPagerWithTab()
        initOnClick()
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

    private fun initViewPagerWithTab() {
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout
        val adapter = JobHistoryDetailViewPager(requireActivity().supportFragmentManager, lifecycle)
        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = jobsArray[position]
        }.attach()
    }
}