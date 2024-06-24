package com.app.freightCyber.presentation.home_dashboard.job.asset_checklist.vehicle

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.BR
import com.app.freightCyber.R
import com.app.freightCyber.domain.model.dummy_data.VehicleInspection
import com.app.freightCyber.databinding.AuthenticationListItemBinding
import com.app.freightCyber.databinding.FragmentVehicleInspectionBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.recycler_view.SimpleRecyclerViewAdapter
import com.app.freightCyber.utils.HelperUtils
import com.app.freightCyber.utils.showToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VehicleInspectionFragment : BaseFragment<FragmentVehicleInspectionBinding>() {

    private var selectedAsset: String? = null
    private val viewModel: VehicleInspectionFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_vehicle_inspection
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view : View) {
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
                    val count = adapter?.list?.count { list -> list.value } ?: 0
                    if(count > 0){
                        if(selectedAsset != null){
                            when(selectedAsset){
                                "TAUTLINER RIGID NO CRANE"->{
                                    val bundle = Bundle()
                                    bundle.putString("FROM", "TAUTLINER RIGID NO CRANE")
                                    HelperUtils.navigateWithSlideAnimations(findNavController() , R.id.inspectionCheckListFragment ,bundle)
                                }
                                "FLAT TOP RIGID NO CRANE"->{
                                    val bundle = Bundle()
                                    bundle.putString("FROM", "FLAT TOP RIGID NO CRANE")
                                    HelperUtils.navigateWithSlideAnimations(findNavController() , R.id.inspectionCheckListFragment , bundle)
                                }
                            }
                        }

                    }else{
                        showToast("Please choose the asset type")
                    }
                }
            }
        })
    }

    private var adapter : SimpleRecyclerViewAdapter<VehicleInspection, AuthenticationListItemBinding>? = null
    @SuppressLint("NotifyDataSetChanged")
    private fun setUpAdapter() {
        adapter =
            SimpleRecyclerViewAdapter(R.layout.vehicle_inspection_list_item,
                BR.bean,
                SimpleRecyclerViewAdapter.SimpleCallback { v, m, pos ->
                    when (v.id) {
                        R.id.imgCheck, R.id.csRoot, R.id.tv1, R.id.tv2 -> {
                            adapter?.list?.forEachIndexed { index, vehicleInspection ->
                                adapter?.list?.get(index)?.value = index == pos
                            }
                            selectedAsset = m.text
                            adapter?.notifyDataSetChanged()
                        }
                    }
                })
        binding.rvVehicleInspection.adapter = adapter
        adapter?.list = authList()
    }


    private fun authList() : ArrayList<VehicleInspection>{
        val list = ArrayList<VehicleInspection>()
        list.add(VehicleInspection(false , "TAUTLINER RIGID NO CRANE"))
        list.add(VehicleInspection(false , "FLAT TOP RIGID NO CRANE"))
        return list
    }
}