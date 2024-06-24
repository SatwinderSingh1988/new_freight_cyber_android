package com.app.freightCyber.presentation.home_dashboard.driver_profile.emergency_contact


import android.os.Bundle
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
import com.app.freightCyber.databinding.FragmentEmergencyDetailBinding
import com.app.freightCyber.domain.model.response.EmergencyData
import com.app.freightCyber.utils.HelperUtils
import com.app.freightCyber.utils.NetworkResult
import com.app.freightCyber.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EmergencyDetailFragment : BaseFragment<FragmentEmergencyDetailBinding>() {

    private val viewModel: EmergencyDetailFragmentVM by viewModels()
    private var emergencyData : EmergencyData? = null

    override fun getLayoutResource(): Int {
        return R.layout.fragment_emergency_detail
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        initObservers()
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.emergencyResult.collectLatest { result ->
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
                            binding.bean = result.data.data
                            emergencyData = result.data.data
                        }

                        null -> {}
                    }
                }
            }
        }
    }

    override fun onResume() {
        viewModel.getEmergencyContactResult()
        super.onResume()
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    findNavController().popBackStack()
                }

                R.id.tvNext -> {
                    val bundle = Bundle()
                    bundle.putParcelable("EMERGENCY_DATA" , emergencyData)
                    HelperUtils.navigateWithSlideAnimations(findNavController(), R.id.emergencyEditFragment ,bundle)
                }
            }
        })
    }

}