package com.app.freightCyber.presentation.home_dashboard.driver_profile.additional_identification

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentAdditionalIdentificationBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.domain.model.response.AdditionalIdentification
import com.app.freightCyber.utils.HelperUtils
import com.app.freightCyber.utils.NetworkResult
import com.app.freightCyber.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AdditionalIdentificationFragment : BaseFragment<FragmentAdditionalIdentificationBinding>() {

    private val viewModel: AdditionalIdentificationFragmentVM by viewModels()

    private var additionalData : AdditionalIdentification? = null

    override fun getLayoutResource(): Int {
        return R.layout.fragment_additional_identification
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.additionalIdentificationResult.collect { result ->
                    when (result) {
                        is NetworkResult.Error -> {
                            hideNewLoading()
                            result.exception.message?.let { showToast(it) }
                        }

                        is NetworkResult.Loading -> {
                            showNewLoading()
                        }

                        is NetworkResult.Success -> {
                            hideNewLoading()
                            additionalData = result.data.data
                            binding.bean = additionalData
                        }
                        null -> {}
                    }
                }
            }
        }
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    findNavController().popBackStack()
                }

                R.id.tvNext -> {
                    HelperUtils.navigateWithSlideAnimations(findNavController() , R.id.addAdditionalFragment)
                }

                R.id.csRoot, R.id.tvTitle, R.id.tvSubTitle, R.id.imgRight -> {
                    val bundle = Bundle()
                    bundle.putParcelable("ADDITIONAL_DATA", additionalData)
                    HelperUtils.navigateWithSlideAnimations(findNavController(), R.id.medicareDetailsFragment , bundle)
                }
            }
        })
    }

    /*private var manageAdapter: SimpleRecyclerViewAdapter<AdditionalIdentificationData, ManageBusinessListItemBinding>? = null
    @SuppressLint("NotifyDataSetChanged")
    private fun setUpAdapter() {
        manageAdapter =
            SimpleRecyclerViewAdapter(R.layout.manage_business_list_item,
                BR.bean,
                SimpleRecyclerViewAdapter.SimpleCallback { v, m, pos ->
                    when (v.id) {
                        R.id.csRoot, R.id.tvTitle, R.id.tvSubTitle, R.id.imgRight -> {
                            val bundle = Bundle()
                            bundle.putParcelable("ADDITIONAL_DATA", additionalData)
                            HelperUtils.navigateWithSlideAnimations(findNavController(), R.id.medicareDetailsFragment , bundle)
                        }
                    }
                })
        binding.rvManage.adapter = manageAdapter
        manageAdapter?.list = empData()
    }

    private fun empData(): ArrayList<EmpData> {
        val list = ArrayList<EmpData>()
        list.add(EmpData("License"))
        list.add(EmpData("License"))
        list.add(EmpData("License"))
        list.add(EmpData("License"))
        return list
    }*/
}