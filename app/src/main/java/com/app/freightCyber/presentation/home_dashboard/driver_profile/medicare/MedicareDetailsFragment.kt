package com.app.freightCyber.presentation.home_dashboard.driver_profile.medicare


import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentMedicareDetailBinding
import com.app.freightCyber.databinding.RemoveBottomSheetBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.bottom_sheet.BaseCustomBottomSheet
import com.app.freightCyber.domain.model.response.AdditionalIdentification
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MedicareDetailsFragment : BaseFragment<FragmentMedicareDetailBinding>() {

    private val viewModel: MedicareDetailsFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_medicare_detail
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        showRemoveBottomSheet()
        getData()
    }

    private fun getData() {
        val data = arguments?.getParcelable<AdditionalIdentification>("ADDITIONAL_DATA")
        binding.bean = data
    }

    private var showRemoveBottomSheet: BaseCustomBottomSheet<RemoveBottomSheetBinding>? = null
    private fun showRemoveBottomSheet() {
        showRemoveBottomSheet =
            BaseCustomBottomSheet(requireContext(),
                R.layout.remove_bottom_sheet,
                BaseCustomBottomSheet.Listener {
                    when (it.id) {
                        R.id.imgBack -> {
                            showRemoveBottomSheet?.dismiss()
                        }

                        R.id.tvRemove -> {
                            showRemoveBottomSheet?.dismiss()
                        }

                        R.id.tvNoKeepIt -> {
                            showRemoveBottomSheet?.dismiss()
                        }
                    }
                })
        showRemoveBottomSheet?.setCancelable(false)
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    findNavController().popBackStack()
                }
                R.id.tvRemove -> {
                    showRemoveBottomSheet?.show()
                }
            }
        })
    }

}