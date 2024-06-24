package com.app.freightCyber.presentation.home_dashboard.ewd.logging_off

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.BR
import com.app.freightCyber.R
import com.app.freightCyber.domain.model.dummy_data.LoggingOff
import com.app.freightCyber.databinding.FragmentLoggingOffFirstBinding
import com.app.freightCyber.databinding.LoggingOfSelectItemBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.recycler_view.SimpleRecyclerViewAdapter
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoggingOffFirstFragment : BaseFragment<FragmentLoggingOffFirstBinding>() {

    private val viewModel: LoggingOffFirstFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_logging_off_first
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        setUpAdapter()
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

    private var innerAdapter: SimpleRecyclerViewAdapter<LoggingOff, LoggingOfSelectItemBinding>? = null
    private fun setUpAdapter() {
        innerAdapter =
            SimpleRecyclerViewAdapter(R.layout.logging_of_select_item,
                BR.bean,
                SimpleRecyclerViewAdapter.SimpleCallback { v, m, pos ->
                    when (m.text) {
                        "Complete unfinished work/rest changes" -> {
                            HelperUtils.navigateWithSlideAnimations(
                                findNavController(),
                                R.id.startWorkFragment
                            )
                        }

                        "Cancel" -> {
                            findNavController().popBackStack()
                        }

                        "Submit diary" -> {
                            HelperUtils.navigateWithSlideAnimations(
                                findNavController(),
                                R.id.submitDiaryFragment
                            )
                        }
                    }
                })
        binding.rvView.adapter = innerAdapter
        innerAdapter?.list = authList2()
    }

    private fun authList2(): ArrayList<LoggingOff> {
        val list = ArrayList<LoggingOff>()
        list.add(LoggingOff("Complete unfinished work/rest changes"))
        list.add(LoggingOff("Maintain current work/rest"))
        list.add(LoggingOff("Submit diary"))
        list.add(LoggingOff("Cancel"))
        return list
    }



}