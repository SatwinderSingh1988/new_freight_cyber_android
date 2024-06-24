package com.app.freightCyber.presentation.home_dashboard.job

import android.view.View
import androidx.fragment.app.viewModels
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentJobBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.presentation.home_dashboard.job.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class JobFragment  : BaseFragment<FragmentJobBinding>(){

    private val viewModel : JobFragmentVM by viewModels()

    private val jobsArray = arrayOf(
        "All Jobs",
        "Accepted",
        "Rejected"
    )

    override fun getLayoutResource(): Int {
        return R.layout.fragment_job
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initViewPagerWithTab()
    }

    private fun initViewPagerWithTab() {
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout
        val adapter = ViewPagerAdapter(requireActivity().supportFragmentManager, lifecycle)
        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = jobsArray[position]
        }.attach()
    }
}