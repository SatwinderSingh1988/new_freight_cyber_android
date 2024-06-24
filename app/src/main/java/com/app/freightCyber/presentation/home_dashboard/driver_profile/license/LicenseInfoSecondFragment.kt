package com.app.freightCyber.presentation.home_dashboard.driver_profile.license


import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.BR
import com.app.freightCyber.R
import com.app.freightCyber.core.base.bottom_sheet.BaseCustomBottomSheet
import com.app.freightCyber.databinding.FragmentLicenseInfoSecondBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.recycler_view.SimpleRecyclerViewAdapter
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.databinding.LicenceTypeItemDesignBinding
import com.app.freightCyber.databinding.LicenseTypeBottomSheetDesignBinding
import com.app.freightCyber.domain.model.dummy_data.LicenceType
import com.app.freightCyber.domain.model.response.DriverLicenseData
import com.app.freightCyber.utils.HelperUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LicenseInfoSecondFragment : BaseFragment<FragmentLicenseInfoSecondBinding>() {

    private var popUplist: List<String>? = null
    private val viewModel: LicenseInfoSecondFragmentVM by viewModels()


    override fun getLayoutResource(): Int {
        return R.layout.fragment_license_info_second
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        popUplist = listOf("item1", "item2", "item3")
        addTextWatcher(binding.tvLicenseEx)
        getData()
        initLicenseBottomSheet()
    }

    private fun getData() {
        val data = arguments?.getParcelable<DriverLicenseData>("DRIVER_LICENSE_DATA")
        binding.bean = data
    }


    private var bottomSheet: BaseCustomBottomSheet<LicenseTypeBottomSheetDesignBinding>? = null
    private fun initLicenseBottomSheet() {
        bottomSheet =
            BaseCustomBottomSheet(
                requireContext(),
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
                            binding.tvLicenseType.setText(m.licenseType)
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


    private fun addTextWatcher(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            var currentLength = 0
            var isDeleting = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                currentLength = s?.length ?: 0
                isDeleting = count > after
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val input = s.toString()
                if (input.length == 2 && currentLength < input.length && !isDeleting) {
                    editText.setText("$input/")
                    editText.setSelection(input.length + 1)
                }
            }
        })
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    findNavController().popBackStack()
                }

                R.id.tvLicenseType -> {
                    bottomSheet?.show()
                }

                R.id.tvIssuingState -> {
                    if(popUplist != null){
                        HelperUtils.showPopupMenu(requireContext() ,binding.tvIssuingState , popUplist!!)
                    }
                }

                R.id.tvCountry -> {
                    if(popUplist != null){
                        HelperUtils.showPopupMenu(requireContext() ,binding.tvCountry , popUplist!!)
                    }
                }
            }
        })
    }
}