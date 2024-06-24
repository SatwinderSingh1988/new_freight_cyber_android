package com.app.freightCyber.presentation.home_dashboard.driver_profile.dse

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.BR
import com.app.freightCyber.R
import com.app.freightCyber.domain.model.dummy_data.DSEModuleData
import com.app.freightCyber.databinding.DseItemListBinding
import com.app.freightCyber.databinding.FragmentDseModuleBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.recycler_view.SimpleRecyclerViewAdapter
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DSEModuleFragment : BaseFragment<FragmentDseModuleBinding>() {

    private val viewModel: DSEModuleFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_dse_module
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

                R.id.tvNext -> {

                }
            }
        })
    }

    private var dseAdapter : SimpleRecyclerViewAdapter<DSEModuleData, DseItemListBinding>? = null
    @SuppressLint("NotifyDataSetChanged")
    private fun setUpAdapter() {
        dseAdapter =
            SimpleRecyclerViewAdapter(R.layout.dse_item_list,
                BR.bean,
                SimpleRecyclerViewAdapter.SimpleCallback { v, m, pos ->
                    when (v.id) {
                        R.id.container, R.id.img, R.id.tvHeader, R.id.tvSubHeader -> {
                            HelperUtils.navigateWithSlideAnimations(
                                findNavController(),
                                R.id.DSEModuleDetailFragment
                            )
                        }
                    }
                })
        binding.rvDSE.adapter = dseAdapter
        dseAdapter?.list = settingList()
    }

    private fun settingList() : ArrayList<DSEModuleData>{
        val list = ArrayList<DSEModuleData>()
        list.add(DSEModuleData(R.drawable.img_drive ,"Introduction to Safe Driving" ,"Learn the fundamental principles of safe driving, including the importance of following traffic laws, wearing seatbelts, and avoiding distractions.","Approximately 30 minutes"))
        list.add(DSEModuleData(R.drawable.img_regulation ,"Understanding Road Regulations" ,"Gain a comprehensive understanding of road regulations, including speed limits, traffic signs, and right-of-way rules.","Approximately 45 minutes"))
        list.add(DSEModuleData(R.drawable.img_emergency ,"Handling Emergencies on the Road" ,"Explore how to effectively respond to common emergencies such as accidents, breakdowns, and adverse weather conditions.","Approximately 60 minutes"))
        list.add(DSEModuleData(R.drawable.img_vehicle ,"Maintaining Vehicle Safety" ,"Learn how to conduct routine vehicle inspections, identify potential issues, and ensure your vehicle is in optimal condition for safe operation.","Approximately 60 minutes"))
        return list
    }
}