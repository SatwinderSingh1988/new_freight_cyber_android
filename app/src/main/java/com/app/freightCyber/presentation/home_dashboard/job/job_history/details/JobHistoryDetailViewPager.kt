package com.app.freightCyber.presentation.home_dashboard.job.job_history.details

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.freightCyber.presentation.home_dashboard.job.all_job.AllJobFragment

private const val NUM_TABS = 2

class JobHistoryDetailViewPager(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return PickupDetailFragment()
            1 -> return DeliveryDetailFragment()
        }
        return AllJobFragment()
    }
}