package com.app.freightCyber.presentation.home_dashboard.compliance.driver_details

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter


private const val NUM_TABS = 2

class ComplianceDriverViewPager(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return SoloFragment()
            1 -> return TwoUpFragment()
        }
        return SoloFragment()
    }
}