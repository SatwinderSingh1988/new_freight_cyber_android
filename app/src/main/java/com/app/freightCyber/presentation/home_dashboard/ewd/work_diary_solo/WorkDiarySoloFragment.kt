package com.app.freightCyber.presentation.home_dashboard.ewd.work_diary_solo

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.BR
import com.app.freightCyber.R
import com.app.freightCyber.domain.model.dummy_data.WorkDiarySolo
import com.app.freightCyber.databinding.FragmentWorkDiarySoloBinding
import com.app.freightCyber.databinding.SoloItemDesignBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.recycler_view.SimpleRecyclerViewAdapter
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WorkDiarySoloFragment : BaseFragment<FragmentWorkDiarySoloBinding>() {

    private val viewModel: WorkDiarySoloFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_work_diary_solo
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        setSoloAdapter()
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

    private var authAdapter : SimpleRecyclerViewAdapter<WorkDiarySolo, SoloItemDesignBinding>? = null
    @SuppressLint("NotifyDataSetChanged")
    private fun setSoloAdapter() {
        authAdapter =
            SimpleRecyclerViewAdapter(R.layout.solo_item_design,
                BR.bean,
                SimpleRecyclerViewAdapter.SimpleCallback { v, m, pos ->
                    when (v.id) {
                        R.id.csRoot -> {
                            HelperUtils.navigateWithSlideAnimations(
                                findNavController(),
                                R.id.WWDFragment
                            )
                        }
                    }
                })
        binding.rvView.adapter = authAdapter
        authAdapter?.list = soloList()
    }


    private fun soloList() : ArrayList<WorkDiarySolo>{
        val list = ArrayList<WorkDiarySolo>()
        list.add(WorkDiarySolo(R.drawable.ewd_first ,"Electronic work diary (EWD)"))
        list.add(WorkDiarySolo(R.drawable.ewd_second ,"Written work diary (WWD)"))
        return list
    }
}