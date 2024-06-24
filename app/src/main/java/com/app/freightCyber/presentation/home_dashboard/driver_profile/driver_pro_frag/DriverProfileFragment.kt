package com.app.freightCyber.presentation.home_dashboard.driver_profile.driver_pro_frag

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.BR
import com.app.freightCyber.R
import com.app.freightCyber.domain.model.dummy_data.SettingData
import com.app.freightCyber.databinding.FragmentDriverProfileBinding
import com.app.freightCyber.databinding.SettingRvListItemBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.recycler_view.SimpleRecyclerViewAdapter
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DriverProfileFragment  : BaseFragment<FragmentDriverProfileBinding>(){

    private val viewModel : DriverProfileFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_driver_profile
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        setUpAdapter()
        getUserDetails()
    }

    private fun getUserDetails() {
        val name = sharedPrefManager.getCurrentUser()?.data?.user?.name
        binding.tvName.text = name
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
                R.id.tvEditProfile -> {
                    HelperUtils.navigateWithSlideAnimations(findNavController() ,R.id.profileSettingsFragment)
                }

            }
        })
    }

    private var settingAdapter: SimpleRecyclerViewAdapter<SettingData, SettingRvListItemBinding>? = null

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpAdapter() {
        settingAdapter =
            SimpleRecyclerViewAdapter(R.layout.setting_rv_list_item,
                BR.bean,
                SimpleRecyclerViewAdapter.SimpleCallback { v, m, pos ->
                    when (m.text) {
                        "License" -> {
                            HelperUtils.navigateWithSlideAnimations(
                                findNavController(),
                                R.id.licenseInfoFirstFragment
                            )
                        }

                        "Emergency Contact" -> {
                            HelperUtils.navigateWithSlideAnimations(
                                findNavController(),
                                R.id.emergencyDetailFragment
                            )
                        }

                        "Transport Operators" -> {
                            HelperUtils.navigateWithSlideAnimations(
                                findNavController(),
                                R.id.transportOperatorFragment
                            )
                        }

                        "Additional Identification" -> {
                            HelperUtils.navigateWithSlideAnimations(
                                findNavController(),
                                R.id.additionalIdentificationFragment
                            )
                        }

                        "Driver Safety Education (DSE)" -> {
                            // HelperUtils.navigateWithSlideAnimations(findNavController() ,R.id.DSEModuleFragment)
                        }

                        "Dark Mode" -> {
                            /* m.isSelect = settingAdapter?.list?.get(pos)?.isSelect != true
                                 settingAdapter?.notifyDataSetChanged()
                            */
                        }
                    }
                })
        binding.rvSettings.adapter = settingAdapter
        settingAdapter?.list = settingList()
        settingAdapter?.notifyDataSetChanged()
    }

    private fun settingList(): ArrayList<SettingData> {
        val list = ArrayList<SettingData>()
        list.add(SettingData(R.drawable.img_license, "License", false))
        list.add(SettingData(R.drawable.img_license, "Emergency Contact", false))
        list.add(SettingData(R.drawable.img_business, "Transport Operators", false))
        list.add(SettingData(R.drawable.img_license, "Additional Identification", false))
        list.add(SettingData(R.drawable.img_dse, "Driver Safety Education (DSE)", false))
        list.add(SettingData(R.drawable.img_dark, "Dark Mode", false))
        return list
    }


}