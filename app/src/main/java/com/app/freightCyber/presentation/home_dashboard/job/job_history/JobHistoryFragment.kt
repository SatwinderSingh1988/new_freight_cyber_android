package com.app.freightCyber.presentation.home_dashboard.job.job_history

import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentJobHistoryBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JobHistoryFragment  : BaseFragment<FragmentJobHistoryBinding>(){

    private val viewModel : JobHistoryFragmentVM by viewModels()
    private lateinit var onBackPressedCallback: OnBackPressedCallback


    private val jobsArray = arrayOf(
        "Completed",
        "Accepted"
    )

    override fun getLayoutResource(): Int {
        return R.layout.fragment_job_history
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initViewPagerWithTab()
        initOnClick()
        handleBackButton()
    }

    private fun handleBackButton() {
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack(R.id.homeFragment , false)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }
    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                  findNavController().popBackStack(R.id.homeFragment , false)
                }
                R.id.imgSearch -> {
                    if(binding.etSearch.visibility == View.VISIBLE){
                        binding.etSearch.visibility = View.GONE
                    }else{
                        binding.etSearch.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun initViewPagerWithTab() {
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout
        val adapter = JobHistoryViewPager(requireActivity().supportFragmentManager, lifecycle)
        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = jobsArray[position]
        }.attach()
    }
}