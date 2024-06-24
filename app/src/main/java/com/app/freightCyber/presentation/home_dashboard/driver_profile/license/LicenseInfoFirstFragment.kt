package com.app.freightCyber.presentation.home_dashboard.driver_profile.license

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.BR
import com.app.freightCyber.R
import com.app.freightCyber.domain.model.dummy_data.AuthenticationData
import com.app.freightCyber.databinding.FragmentLicenseInfoFirstBinding
import com.app.freightCyber.databinding.LicenseListItemBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.recycler_view.SimpleRecyclerViewAdapter
import com.app.freightCyber.domain.model.response.DriverLicenseData
import com.app.freightCyber.utils.HelperUtils
import com.app.freightCyber.utils.NetworkResult
import com.app.freightCyber.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LicenseInfoFirstFragment : BaseFragment<FragmentLicenseInfoFirstBinding>() {

    private val viewModel: LicenseInfoFirstFragmentVM by viewModels()
    var listOfDriverLicense = ArrayList<DriverLicenseData>()


    override fun getLayoutResource(): Int {
        return R.layout.fragment_license_info_first
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        setUpAdapter()
        initObservers()
    }


    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.driverLicenseResult.collect { result ->
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
                            showToast(result.data.message)
                            listOfDriverLicense = result.data.data as? ArrayList<DriverLicenseData> ?: arrayListOf()
                            authAdapter?.list = listOfDriverLicense
                        }
                        null -> {}
                    }
                }
            }
        }
    }


    private var authAdapter: SimpleRecyclerViewAdapter<DriverLicenseData, LicenseListItemBinding>? =
        null

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpAdapter() {
        authAdapter =
            SimpleRecyclerViewAdapter(R.layout.license_list_item,
                BR.bean,
                SimpleRecyclerViewAdapter.SimpleCallback { v, m, pos ->
                    when (v.id) {
                        R.id.root, R.id.tv1, R.id.tv2, R.id.tvOld, R.id.imgRight -> {
                            val bundle = Bundle()
                            bundle.putParcelable("DRIVER_LICENSE_DATA" , m)
                            HelperUtils.navigateWithSlideAnimations(findNavController(), R.id.licenseInfoSecondFragment , bundle)
                        }
                    }
                })
        binding.rvLicense.adapter = authAdapter
        authAdapter?.notifyDataSetChanged()
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
        return list
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
}