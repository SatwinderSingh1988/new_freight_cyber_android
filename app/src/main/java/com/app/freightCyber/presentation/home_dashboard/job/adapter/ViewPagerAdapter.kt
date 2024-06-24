package com.app.freightCyber.presentation.home_dashboard.job.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.freightCyber.presentation.home_dashboard.job.accepted_job.AcceptedJobFragment
import com.app.freightCyber.presentation.home_dashboard.job.all_job.AllJobFragment
import com.app.freightCyber.presentation.home_dashboard.job.rejected_job.RejectedJobFragment

private const val NUM_TABS = 3

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return AllJobFragment()
            1 -> return AcceptedJobFragment()
            2 -> return RejectedJobFragment()
        }
        return AllJobFragment()
    }
}
