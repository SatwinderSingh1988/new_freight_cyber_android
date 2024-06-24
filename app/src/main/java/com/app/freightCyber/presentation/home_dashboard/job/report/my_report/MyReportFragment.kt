package com.app.freightCyber.presentation.home_dashboard.job.report.my_report

import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentMyReportBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.utils.HelperUtils
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyReportFragment : BaseFragment<FragmentMyReportBinding>() {

    private val viewModel: MyReportFragmentVM by viewModels()
    private lateinit var onBackPressedCallback: OnBackPressedCallback


    private val reportArray = arrayOf(
        "Delays",
        "Accidents"
    )

    override fun getLayoutResource(): Int {
        return R.layout.fragment_my_report
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        initViewPagerWithTab()
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    findNavController().popBackStack()
                }
                R.id.tvReportAnIncident -> {
                    HelperUtils.navigateWithSlideAnimations(findNavController() , R.id.reportIssueFragment)
                }
            }
        })
    }

    private fun handleBackButton() {
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack(R.id.reportIssueFragment , false)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }
    private fun initViewPagerWithTab() {
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout
        val adapter = ReportViewPagerAdapter(requireActivity().supportFragmentManager, lifecycle)
        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = reportArray[position]
        }.attach()
    }
}