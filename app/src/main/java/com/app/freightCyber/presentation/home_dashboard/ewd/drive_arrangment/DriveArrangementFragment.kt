package com.app.freightCyber.presentation.home_dashboard.ewd.drive_arrangment

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.BR
import com.app.freightCyber.R
import com.app.freightCyber.domain.model.dummy_data.DriveArrangement
import com.app.freightCyber.databinding.DriveArrangmentListItemBinding
import com.app.freightCyber.databinding.FragmentDriverArrangmentBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.recycler_view.SimpleRecyclerViewAdapter
import com.app.freightCyber.presentation.home_dashboard.ewd.ewd_profile_details.EwdProfileDetails
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DriveArrangementFragment : BaseFragment<FragmentDriverArrangmentBinding>() {

    private val viewModel: DriveArrangementFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_driver_arrangment
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        setDriveArrangeAdapter()
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    findNavController().popBackStack()
                }
            }
        })
    }

    private var authAdapter: SimpleRecyclerViewAdapter<DriveArrangement, DriveArrangmentListItemBinding>? =
        null

    @SuppressLint("NotifyDataSetChanged")
    private fun setDriveArrangeAdapter() {
        authAdapter =
            SimpleRecyclerViewAdapter(
                R.layout.drive_arrangment_list_item, BR.bean,
                SimpleRecyclerViewAdapter.SimpleCallback { v, m, pos ->
                    when (m.type) {
                        "Solo" -> {
                            EwdProfileDetails.from = "SOLO"
                            HelperUtils.navigateWithSlideAnimations(
                                findNavController(),
                                R.id.ewdProfileDetails
                            )
                        }

                        "2 UP" -> {
                            EwdProfileDetails.from = "2 UP"
                            HelperUtils.navigateWithSlideAnimations(
                                findNavController(),
                                R.id.ewdProfileDetails
                            )
                        }
                    }
                })
        binding.rvView.adapter = authAdapter
        authAdapter?.list = soloList()
    }

    private fun soloList(): ArrayList<DriveArrangement> {
        val list = ArrayList<DriveArrangement>()
        list.add(
            DriveArrangement(
                R.drawable.profile_solo,
                "Solo",
                "Only one person in the vehicle responsible for driving."
            )
        )
        list.add(
            DriveArrangement(
                R.drawable.profile_2up,
                "2 UP",
                "Driving with two occupants: one main driver, one assistant."
            )
        )
        return list
    }
}
