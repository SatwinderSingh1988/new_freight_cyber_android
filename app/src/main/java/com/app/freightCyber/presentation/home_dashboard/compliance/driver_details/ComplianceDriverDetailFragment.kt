package com.app.freightCyber.presentation.home_dashboard.compliance.driver_details

import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentComplianceDriverDetailBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ComplianceDriverDetailFragment : BaseFragment<FragmentComplianceDriverDetailBinding>() {

    private val viewModel: ComplianceDriverDetailFragmentVM by viewModels()
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    private val driverArray = arrayOf(
        "Solo",
        "2-Up"
    )

    override fun getLayoutResource(): Int {
        return R.layout.fragment_compliance_driver_detail
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        initViewPagerWithTab()
      //  handleBackButton()
    }

    private fun handleBackButton() {
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack(R.id.loadingDataFragment , true)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
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
        val adapter = ComplianceDriverViewPager(requireActivity().supportFragmentManager, lifecycle)
        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = driverArray[position]
        }.attach()
    }

}