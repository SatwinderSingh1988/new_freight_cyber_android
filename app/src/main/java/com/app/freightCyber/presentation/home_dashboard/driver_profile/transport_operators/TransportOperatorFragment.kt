package com.app.freightCyber.presentation.home_dashboard.driver_profile.transport_operators

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.BR
import com.app.freightCyber.R
import com.app.freightCyber.domain.model.dummy_data.EmpData
import com.app.freightCyber.databinding.AddNewEmpDesignBinding
import com.app.freightCyber.databinding.EmpListItemBinding
import com.app.freightCyber.databinding.FragmentTransportOperatorBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.recycler_view.SimpleRecyclerViewAdapter
import com.app.freightCyber.core.base.bottom_sheet.BaseCustomBottomSheet
import com.app.freightCyber.domain.model.request.TransportOperatorRequest
import com.app.freightCyber.utils.HelperUtils
import com.app.freightCyber.utils.NetworkResult
import com.app.freightCyber.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TransportOperatorFragment : BaseFragment<FragmentTransportOperatorBinding>() {

    private val viewModel: TransportOperatorFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_transport_operator
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        setUpAdapter()
        addNewEmpBottomSheet()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.getTransportResult.collect { result ->
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
                                viewModel.resetAndUpdateTransportOperatorResult()
                            }

                            null -> {}
                        }
                    }
                }
                launch {
                    viewModel.transportResult.collect { result ->
                        when (result) {
                            is NetworkResult.Error -> {
                                hideNewLoading()
                                showToast(result.exception.message.toString())
                            }

                            is NetworkResult.Loading -> {
                                showNewLoading()
                            }

                            is NetworkResult.Success -> {
                                hideNewLoading()
                                showToast(result.data.message.toString())
                            }

                            null -> {}
                        }
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

                R.id.tvAddNewEmployer -> {
                    addNEwEmpBottomSheet?.show()
                }
            }
        })
    }

    private var addNEwEmpBottomSheet: BaseCustomBottomSheet<AddNewEmpDesignBinding>? = null
    private fun addNewEmpBottomSheet() {
        addNEwEmpBottomSheet =
            BaseCustomBottomSheet(requireContext(),
                R.layout.add_new_emp_design,
                BaseCustomBottomSheet.Listener {
                    when (it.id) {
                        R.id.imgBack -> {
                            addNEwEmpBottomSheet?.dismiss()
                        }

                        R.id.tvSend -> {
                            if (TextUtils.isEmpty(addNEwEmpBottomSheet?.binding?.etTransportOperator?.text.toString())) {
                                showToast("please fill the code")
                                return@Listener
                            }

                            viewModel.postTransportData(TransportOperatorRequest(tenant_code = addNEwEmpBottomSheet?.binding?.etTransportOperator?.text.toString()))
                        }

                        R.id.tvSendRequest -> {
                            addNEwEmpBottomSheet?.dismiss()
                        }
                    }
                })
        addNEwEmpBottomSheet?.binding?.rbYes?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                addNEwEmpBottomSheet?.binding?.tv2?.text = "Transport Operator Code"
                addNEwEmpBottomSheet?.binding?.etTransportOperator?.hint = "Enter Code"
            }
        }
        addNEwEmpBottomSheet?.binding?.rbNo?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                addNEwEmpBottomSheet?.binding?.tv2?.text = "Transport Operator"
                addNEwEmpBottomSheet?.binding?.etTransportOperator?.hint = "Search and Select"
            }
        }
        addNEwEmpBottomSheet?.setCancelable(false)
    }


    private var empAdapter: SimpleRecyclerViewAdapter<EmpData, EmpListItemBinding>? = null

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpAdapter() {
        empAdapter =
            SimpleRecyclerViewAdapter(R.layout.emp_list_item,
                BR.bean,
                SimpleRecyclerViewAdapter.SimpleCallback { v, m, pos ->
                    when (v.id) {
                        R.id.root, R.id.tv1, R.id.tvSubTitle, R.id.imgRight -> {
                            HelperUtils.navigateWithSlideAnimations(
                                findNavController(),
                                R.id.employerDetailFragment
                            )
                        }
                    }
                })
        binding.rvEmployer.adapter = empAdapter
        empAdapter?.list = empData()
    }

    private fun empData(): ArrayList<EmpData> {
        val list = ArrayList<EmpData>()
        list.add(EmpData("License"))
        list.add(EmpData("License"))
        list.add(EmpData("License"))
        list.add(EmpData("License"))
        return list
    }
}