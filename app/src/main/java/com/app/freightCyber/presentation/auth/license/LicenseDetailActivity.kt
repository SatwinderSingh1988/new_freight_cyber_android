package com.app.freightCyber.presentation.auth.license

import android.annotation.SuppressLint
import android.content.Intent
import android.text.TextUtils
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.app.freightCyber.R
import com.app.freightCyber.BR
import com.app.freightCyber.domain.model.dummy_data.LicenceType
import com.app.freightCyber.databinding.ActivityLicenseDetailsBinding
import com.app.freightCyber.databinding.LicenceTypeItemDesignBinding
import com.app.freightCyber.databinding.LicenseTypeBottomSheetDesignBinding
import com.app.freightCyber.presentation.auth.udi_generate.UDIGenerateActivity
import com.app.freightCyber.core.base.activity.BaseActivity
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.recycler_view.SimpleRecyclerViewAdapter
import com.app.freightCyber.core.base.bottom_sheet.BaseCustomBottomSheet
import com.app.freightCyber.domain.model.request.DriverLicenseRequest
import com.app.freightCyber.utils.HelperUtils
import com.app.freightCyber.utils.NetworkResult
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LicenseDetailActivity : BaseActivity<ActivityLicenseDetailsBinding>() {

    private val viewModel: LicenseDetailActivityVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.activity_license_details
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView() {
        setStatusTextColor()
        initOnClick()
        initLicenseBottomSheet()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.driverLicenseResult.collect { result ->
                    when (result) {
                        is NetworkResult.Error -> {
                            hideNewLoading()
                            showToast(result.exception.message)
                        }

                        is NetworkResult.Loading -> {
                            showNewLoading()
                        }

                        is NetworkResult.Success -> {
                            hideNewLoading()
                            showToast(result.data.message)
                            goToNextScreen()
                            viewModel.resetUpdateDriverLicenseResult()
                        }

                        null -> {}
                    }
                }
            }
        }
    }

    private fun goToNextScreen() {
        val intent = Intent(this, UDIGenerateActivity::class.java)
        startActivity(intent)
    }

    private fun setStatusTextColor() {
        window.decorView.systemUiVisibility = 0
    }

    private fun initOnClick() {
        viewModel.onClick.observe(this, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    onBackPressedDispatcher.onBackPressed()
                }
                R.id.tvGoBack -> {
                    onBackPressedDispatcher.onBackPressed()
                }

                R.id.etLicenseExDate -> {
                 HelperUtils.openDatePickerDialog(this ,binding.etLicenseExDate)
                }

                R.id.tvNext -> {
                   checkValidation()
                }

                R.id.etLicenseType -> {
                    bottomSheet?.show()
                }
                R.id.etIssuingState -> {
                  HelperUtils.showPopupMenu(this , binding.etIssuingState , listOf("Queensland" ,"Tasmania","New South Wales","Victoria","Western Australia","South Australia"))
                }
            }
        })
    }

    private fun checkValidation() {
        if (TextUtils.isEmpty(binding.etLicenseType.text.toString())) {
            showToast(getString(R.string.please_fill_all_the_details))
            return
        }
        if (TextUtils.isEmpty(binding.etLicenseNo.text.toString())) {
            showToast(getString(R.string.please_fill_all_the_details))
            return
        }
        if (TextUtils.isEmpty(binding.etLicenseExDate.text.toString())) {
            showToast(getString(R.string.please_fill_all_the_details))
            return
        }
        if (TextUtils.isEmpty(binding.etCardNo.text.toString())) {
            showToast(getString(R.string.please_fill_all_the_details))
            return
        }
        if (TextUtils.isEmpty(binding.etIssuingState.text.toString())) {
            showToast(getString(R.string.please_fill_all_the_details))
            return
        }
        if (TextUtils.isEmpty(binding.etAddress.text.toString())) {
            showToast(getString(R.string.please_fill_all_the_details))
            return
        }
        if (TextUtils.isEmpty(binding.etPastCode.text.toString())) {
            showToast(getString(R.string.please_fill_all_the_details))
            return
        }

        if (TextUtils.isEmpty(binding.etCountry.text.toString())) {
            showToast(getString(R.string.please_fill_all_the_details))
            return
        }

        viewModel.createDriverLicense(
            DriverLicenseRequest(license_type = binding.etLicenseType.text.toString(),
                license_number =  binding.etLicenseNo.text.toString(),
                license_expiry_date = binding.etLicenseExDate.text.toString(),
                license_card_number = binding.etCardNo.text.toString(),
                license_issuing_state = binding.etIssuingState.text.toString(),
                address = binding.etAddress.text.toString(),
                post_code = binding.etPastCode.text.toString(),
                country = binding.etCountry.text.toString()
            )
        )

    }

    private var bottomSheet: BaseCustomBottomSheet<LicenseTypeBottomSheetDesignBinding>? = null
    private fun initLicenseBottomSheet() {
        bottomSheet =
            BaseCustomBottomSheet(
                this@LicenseDetailActivity,
                R.layout.license_type_bottom_sheet_design,
                BaseCustomBottomSheet.Listener {
                    when (it.id) {
                        R.id.imgBack -> {
                            bottomSheet?.dismiss()
                        }

                        R.id.tvSelect -> {
                            bottomSheet?.dismiss()
                        }
                    }
                })
        if (bottomSheet != null) {
            initLicenseAdapter(bottomSheet!!)
        }
        bottomSheet?.behavior?.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheet?.setCancelable(true)
    }

    private var adapter: SimpleRecyclerViewAdapter<LicenceType, LicenceTypeItemDesignBinding>? = null

    @SuppressLint("NotifyDataSetChanged")
    private fun initLicenseAdapter(bottomSheet: BaseCustomBottomSheet<LicenseTypeBottomSheetDesignBinding>) {
        adapter =
            SimpleRecyclerViewAdapter(
                R.layout.licence_type_item_design,
                BR.bean,
                SimpleRecyclerViewAdapter.SimpleCallback { v, m, pos ->
                    when (v.id) {
                        R.id.imgCheck, R.id.tvYourBioM, R.id.csRoot -> {
                            /*m.checked = adapter?.list?.get(pos)?.checked == false
                            adapter?.notifyDataSetChanged()*/
                            for (i in 0 until adapter?.list?.size!!) {
                                adapter?.list?.get(i)?.checked = false
                            }
                            adapter?.list?.get(pos)?.checked = true
                            adapter?.notifyDataSetChanged()
                            binding.etLicenseType.setText(m.licenseType)
                        }
                    }
                })
        bottomSheet.binding?.rvLicenseType?.adapter = adapter
        adapter?.list = licenceTypeList()
    }


    private fun licenceTypeList(): ArrayList<LicenceType> {
        val list = ArrayList<LicenceType>()
        list.add(LicenceType(R.drawable.img_uncheck, "Car (C) License", false))
        list.add(LicenceType(R.drawable.img_uncheck, "Rider (R) License", false))
        list.add(LicenceType(R.drawable.img_uncheck, "Restricted Rider (RE) License", false))
        list.add(LicenceType(R.drawable.img_uncheck, "Light Rigid (LR) License", false))
        list.add(LicenceType(R.drawable.img_uncheck, "Medium Rigid (MR) License", false))
        list.add(LicenceType(R.drawable.img_uncheck, "Heavy Rigid (HR) License", false))
        list.add(LicenceType(R.drawable.img_uncheck, "Heavy Combination (HC) License", false))
        list.add(LicenceType(R.drawable.img_uncheck, "Multi-Combination (MC) License", false))
        return list
    }


}