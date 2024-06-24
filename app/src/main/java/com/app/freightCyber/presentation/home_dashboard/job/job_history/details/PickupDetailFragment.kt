package com.app.freightCyber.presentation.home_dashboard.job.job_history.details

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.BR
import com.app.freightCyber.R
import com.app.freightCyber.domain.model.dummy_data.JockeyDetails
import com.app.freightCyber.databinding.FragmentPickupDetailBinding
import com.app.freightCyber.databinding.JockeyDetailBotomSheetBinding
import com.app.freightCyber.databinding.JockeyDetailsItemDesignBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.recycler_view.SimpleRecyclerViewAdapter
import com.app.freightCyber.core.base.bottom_sheet.BaseCustomBottomSheet
import com.app.freightCyber.utils.HelperUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PickupDetailFragment : BaseFragment<FragmentPickupDetailBinding>() {

    private val viewModel: PickupDetailFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_pickup_detail
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        initJockeyDetailsBottomSheet()
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    findNavController().popBackStack()
                }
                R.id.viewAssign -> {
                    HelperUtils.navigateWithSlideAnimations(findNavController() , R.id.assignedDriverFragment)
                }
                R.id.tvView->{
                    bottomSheet?.show()
                }
            }
        })
    }

    private var bottomSheet: BaseCustomBottomSheet<JockeyDetailBotomSheetBinding>? = null
    private fun initJockeyDetailsBottomSheet() {
        bottomSheet =
            BaseCustomBottomSheet(
                requireContext(),
                R.layout.jockey_detail_botom_sheet,
                BaseCustomBottomSheet.Listener {
                    when (it.id) {
                        R.id.imgBack -> {
                            bottomSheet?.dismiss()
                        }

                        R.id.tvClose -> {
                            bottomSheet?.dismiss()
                        }
                    }
                })
        if (bottomSheet != null) {
            initJockeyDetails(bottomSheet!!)
        }
        bottomSheet?.behavior?.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheet?.setCancelable(true)
    }

    private var adapter: SimpleRecyclerViewAdapter<JockeyDetails, JockeyDetailsItemDesignBinding>? = null

    @SuppressLint("NotifyDataSetChanged")
    private fun initJockeyDetails(bottomSheet: BaseCustomBottomSheet<JockeyDetailBotomSheetBinding>) {
        adapter =
            SimpleRecyclerViewAdapter(
                R.layout.jockey_details_item_design,
                BR.bean,
                SimpleRecyclerViewAdapter.SimpleCallback { v, m, pos ->
                    when (v.id) {

                    }
                })
        bottomSheet.binding?.rvJockeyDetails?.adapter = adapter
        adapter?.list = jockeyDetails()
    }

    private fun jockeyDetails(): ArrayList<JockeyDetails> {
        val list = ArrayList<JockeyDetails>()
        list.add(JockeyDetails("Emily Johnson"))
        list.add(JockeyDetails("Michael Brown"))
        list.add(JockeyDetails("Sarah Clark"))
        list.add(JockeyDetails("David White"))
        return list
    }
}
