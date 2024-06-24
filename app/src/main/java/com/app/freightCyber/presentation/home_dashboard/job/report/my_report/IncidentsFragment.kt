package com.app.freightCyber.presentation.home_dashboard.job.report.my_report

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.BR
import com.app.freightCyber.R
import com.app.freightCyber.domain.model.dummy_data.AuthenticationData
import com.app.freightCyber.databinding.FragmentIncidentsBinding
import com.app.freightCyber.databinding.MyReportItemListBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.recycler_view.SimpleRecyclerViewAdapter
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class IncidentsFragment : BaseFragment<FragmentIncidentsBinding>() {

    private val viewModel: IncidentsFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_incidents
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        setReportIncidentAdapter()
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {

            }
        })
    }

    private var authAdapter: SimpleRecyclerViewAdapter<AuthenticationData, MyReportItemListBinding>? = null

    @SuppressLint("NotifyDataSetChanged")
    private fun setReportIncidentAdapter() {
        authAdapter =
            SimpleRecyclerViewAdapter(R.layout.my_report_item_list,
                BR.bean,
                SimpleRecyclerViewAdapter.SimpleCallback { v, m, pos ->
                    when (v.id) {
                        R.id.root, R.id.tv1, R.id.tv2, R.id.tvOld -> {
                            HelperUtils.navigateWithSlideAnimations(
                                findNavController(),
                                R.id.reportIncidentDetailFragment
                            )
                        }
                    }
                })
        binding.rvReportIncident.adapter = authAdapter
        authAdapter?.list = authList()
    }


    private fun authList(): ArrayList<AuthenticationData> {
        val list = ArrayList<AuthenticationData>()
        list.add(
            AuthenticationData(
                R.drawable.img_fingerprint,
                "1. Enable Fingerprint",
                "Tap to set up fingerprint",
                false
            )
        )
        list.add(
            AuthenticationData(
                R.drawable.img_facial,
                "2. Facial Recognition",
                "Tap to set up face",
                false
            )
        )
        list.add(
            AuthenticationData(
                R.drawable.img_audio,
                "3. Voice Registration",
                "Tap to set up voice",
                false
            )
        )
        list.add(
            AuthenticationData(
                R.drawable.img_pass_code,
                "4. Passcode",
                "Tap to set up voice",
                false
            )
        )
        list.add(
            AuthenticationData(
                R.drawable.img_fingerprint,
                "1. Enable Fingerprint",
                "Tap to set up fingerprint",
                false
            )
        )
        list.add(
            AuthenticationData(
                R.drawable.img_facial,
                "2. Facial Recognition",
                "Tap to set up face",
                false
            )
        )
        list.add(
            AuthenticationData(
                R.drawable.img_audio,
                "3. Voice Registration",
                "Tap to set up voice",
                false
            )
        )
        list.add(
            AuthenticationData(
                R.drawable.img_pass_code,
                "4. Passcode",
                "Tap to set up voice",
                false
            )
        )
        return list
    }
}