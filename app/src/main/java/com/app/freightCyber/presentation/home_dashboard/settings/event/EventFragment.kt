package com.app.freightCyber.presentation.home_dashboard.settings.event

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.BR
import com.app.freightCyber.R
import com.app.freightCyber.domain.model.dummy_data.EventLog
import com.app.freightCyber.databinding.EventListItemBinding
import com.app.freightCyber.databinding.FragmentEventBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.recycler_view.SimpleRecyclerViewAdapter
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventFragment : BaseFragment<FragmentEventBinding>() {

    private val viewModel: EventFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_event
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        setEventAdapter()
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    findNavController().popBackStack()
                }
                R.id.imgProfile -> {
                   HelperUtils.navigateWithSlideAnimations(findNavController() , R.id.profileSettingsFragment)
                }
            }
        })
    }

    private var adapter : SimpleRecyclerViewAdapter<EventLog, EventListItemBinding>? = null
    @SuppressLint("NotifyDataSetChanged")
    private fun setEventAdapter() {
        adapter =
            SimpleRecyclerViewAdapter(R.layout.event_list_item,
                BR.bean,
                SimpleRecyclerViewAdapter.SimpleCallback { v, m, pos ->
                    when (v.id) {
                        R.id.imgCheck, R.id.csRoot, R.id.tv1, R.id.tv2 -> {

                        }
                    }
                })
        binding.rvEvent.adapter = adapter
        adapter?.list = authList()
    }


    private fun authList() : ArrayList<EventLog>{
        val list = ArrayList<EventLog>()
        list.add(EventLog("Work"))
        list.add(EventLog("Rest"))
        list.add(EventLog("Work"))
        list.add(EventLog("Rest"))
        return list
    }

}