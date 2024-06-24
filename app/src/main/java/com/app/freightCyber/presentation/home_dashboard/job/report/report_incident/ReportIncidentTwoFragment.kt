package com.app.freightCyber.presentation.home_dashboard.job.report.report_incident

import android.app.Activity
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentReportIncidentTwoBinding
import com.app.freightCyber.databinding.ReportIncidentBottomSheetBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.bottom_sheet.BaseCustomBottomSheet
import com.app.freightCyber.utils.HelperUtils
import com.app.freightCyber.utils.showToast
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ReportIncidentTwoFragment : BaseFragment<FragmentReportIncidentTwoBinding>() {

    private var amPmList: List<String>? = null
    private val viewModel: ReportIncidentTwoFragmentVM by viewModels()

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                val fileUri = data?.data!!
                //   binding.imgProfile.setImageURI(fileUri)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                showToast(ImagePicker.getError(data))
            } else {
                showToast("Task Cancelled")
            }
        }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_report_incident_two
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        incidentReportBottomSheet()
        initOnClick()
        amPmList = listOf<String>("AM", "PM")
        checkSwitchClicks()
    }

    private fun checkSwitchClicks() {
        binding.sbAnyVisibleInjuries.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                binding.csAnyVisibleInjuries.visibility = View.VISIBLE
            }else{
                binding.csAnyVisibleInjuries.visibility = View.GONE
            }
        }

        binding.cbVisibleAssetDamage.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                binding.csVisibleAssetDamage.visibility = View.VISIBLE
            }else{
                binding.csVisibleAssetDamage.visibility = View.GONE
            }
        }

        binding.sbAnyExternalVehicleDamages.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                binding.csAnyExternalVehicleDamages.visibility = View.VISIBLE
            }else{
                binding.csAnyExternalVehicleDamages.visibility = View.GONE
            }
        }

        binding.sbPropertyDamage.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                binding.csPropertyDamage.visibility = View.VISIBLE
            }else{
                binding.csPropertyDamage.visibility = View.GONE
            }
        }

        binding.cbPoliceAuth.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                binding.csPoliceAuth.visibility = View.VISIBLE
            }else{
                binding.csPoliceAuth.visibility = View.GONE
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
                    HelperUtils.navigateWithSlideAnimations(
                        findNavController(),
                        R.id.reportSuccessFragment
                    )
                }

                R.id.viewCertificate, R.id.imgPlus -> {
                    ImagePicker.with(this)
                        .galleryOnly()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .createIntent { intent ->
                            startForProfileImageResult.launch(intent)
                        }
                }

                R.id.etDateTimeOfAccident -> {
                    HelperUtils.openDatePickerDialog(requireContext(), binding.etDateTimeOfAccident)
                }

                R.id.etType -> {
                    incidentTypeBottomSheet?.show()
                }

                R.id.etVehicleInvolved -> {
                    HelperUtils.showPopupMenu(
                        requireContext(),
                        binding.etVehicleInvolved,
                        listOf("item1", "item2", "item3")
                    )
                }
            }
        })
    }

    private var incidentTypeBottomSheet: BaseCustomBottomSheet<ReportIncidentBottomSheetBinding>? =
        null

    private fun incidentReportBottomSheet() {
        incidentTypeBottomSheet =
            BaseCustomBottomSheet(requireContext(),
                R.layout.report_incident_bottom_sheet,
                BaseCustomBottomSheet.Listener {
                    when (it.id) {
                        R.id.imgBack -> {
                            incidentTypeBottomSheet?.dismiss()
                        }

                        R.id.tvSelect -> {
                            incidentTypeBottomSheet?.dismiss()
                        }

                        R.id.tvNoKeepIt -> {
                            incidentTypeBottomSheet?.dismiss()
                        }
                    }
                })
        incidentTypeBottomSheet?.setCancelable(false)
    }

}