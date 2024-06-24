package com.app.freightCyber.presentation.home_dashboard.driver_profile.emergency_contact


import android.text.TextUtils
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.databinding.FragmentEmergencyEditBinding
import com.app.freightCyber.domain.model.request.EmergencyContactRequest
import com.app.freightCyber.domain.model.response.EmergencyData
import com.app.freightCyber.utils.HelperUtils
import com.app.freightCyber.utils.NetworkResult
import com.app.freightCyber.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EmergencyEditFragment : BaseFragment<FragmentEmergencyEditBinding>() {

    private val viewModel: EmergencyEditFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_emergency_edit
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        getData()
        initObservers()
    }

    private fun getData() {
        val data = arguments?.getParcelable<EmergencyData>("EMERGENCY_DATA")
        binding.bean = data
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.emergencyResult.collect { result ->
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
                            result.data.message?.let { showToast(it) }
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
                    checkValidation()
                }

                R.id.tvGoBack -> {
                    findNavController().popBackStack()
                }

                R.id.etRelationToUser -> {
                    HelperUtils.showPopupMenu(
                        requireContext(),
                        binding.etRelationToUser,
                        listOf("item1", "item2", "item3")
                    )
                }
            }
        })
    }

    private fun checkValidation() {
        if (TextUtils.isEmpty(binding.etContactName.text.toString())) {
            showToast(getString(R.string.please_fill_all_the_details))
            return
        }
        if (TextUtils.isEmpty(binding.etRelationToUser.text.toString())) {
            showToast(getString(R.string.please_fill_all_the_details))
            return
        }
        if (TextUtils.isEmpty(binding.etPhoneNumber.text.toString())) {
            showToast(getString(R.string.please_fill_all_the_details))
            return
        }
        if (TextUtils.isEmpty(binding.etEmail.text.toString())) {
            showToast(getString(R.string.please_fill_all_the_details))
            return
        }
        viewModel.updateEmergencyContactResult(
            EmergencyContactRequest(
                emerg_contact_full_name = binding.etContactName.text.toString(),
                emerg_contact_relationship = binding.etRelationToUser.text.toString(),
                emerg_contact_full_phone = binding.etPhoneNumber.text.toString(),
                emerg_contact_email = binding.etEmail.text.toString()
            )
        )
    }

}