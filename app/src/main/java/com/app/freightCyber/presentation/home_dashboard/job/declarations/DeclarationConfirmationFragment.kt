package com.app.freightCyber.presentation.home_dashboard.job.declarations


import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.BR
import com.app.freightCyber.R
import com.app.freightCyber.domain.model.dummy_data.DeclarationList
import com.app.freightCyber.databinding.DeclarationListItemBinding
import com.app.freightCyber.databinding.FragmentDeclarationBinding
import com.app.freightCyber.databinding.SignatureBottomSheetBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.recycler_view.SimpleRecyclerViewAdapter
import com.app.freightCyber.core.base.bottom_sheet.BaseCustomBottomSheet
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DeclarationConfirmationFragment : BaseFragment<FragmentDeclarationBinding>() {

    private val TAG: String = "DIGITAL"
    private val viewModel: DeclarationConfirmationFragmentVM by viewModels()


    override fun getLayoutResource(): Int {
        return R.layout.fragment_declaration
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view : View) {
        initOnClick()
        setUpAdapter()
        showSignBottomSheet()
    }


    private var signBottomSheet : BaseCustomBottomSheet<SignatureBottomSheetBinding>? = null
    private fun showSignBottomSheet() {
            signBottomSheet =
                BaseCustomBottomSheet(requireContext(),
                    R.layout.signature_bottom_sheet,
                    BaseCustomBottomSheet.Listener {
                        when (it.id) {
                            R.id.imgBack -> {
                                signBottomSheet?.dismiss()
                            }

                            R.id.tvCancel -> {
                                signBottomSheet?.dismiss()
                            }

                            R.id.tvAddSign -> {
                                signBottomSheet?.dismiss()
                            }
                        }
                    })
            signBottomSheet?.setCancelable(false)
    }

    private var innerAdapter: SimpleRecyclerViewAdapter<DeclarationList, DeclarationListItemBinding>? = null
    private fun setUpAdapter() {
        innerAdapter =
            SimpleRecyclerViewAdapter(R.layout.declaration_list_item,
                BR.bean,
                SimpleRecyclerViewAdapter.SimpleCallback { v, m, pos ->
                    when (v.id) {
                        R.id.root, R.id.tv1, R.id.tv2, R.id.tvOld, R.id.imgRight -> {

                        }
                    }
                })
        binding.rvDeclaration.adapter = innerAdapter
        innerAdapter?.list = authList2()
    }

    private fun authList2(): ArrayList<DeclarationList> {
        val list = ArrayList<DeclarationList>()
        list.add(DeclarationList("I declare I am mentally and physically fit. I have taken my required rest periods and I am able to perform my normal workplace duties."))
        list.add(DeclarationList("I declare I am not under the influence of alcohol or illicit drugs."))
        list.add(DeclarationList("I declare that I carry a current valid Driver's Licence that is appropriate for this vehicle."))
        list.add(DeclarationList("I am wearing the correct PPE."))
        list.add(DeclarationList(" I have been trained to perform my task to the correct standards."))
        return list
    }



    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                   findNavController().popBackStack()
                }

                R.id.tvNext -> {
                    HelperUtils.navigateWithSlideAnimations(findNavController() , R.id.inspectionSuccessFullyFragment)
                }

                R.id.signView -> {
                    signBottomSheet?.show()
                }
            }
        })
    }

}