package com.app.freightCyber.presentation.home_dashboard.job.asset_checklist.inspection

import android.annotation.SuppressLint
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.BR
import com.app.freightCyber.R
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.recycler_view.SimpleRecyclerViewAdapter
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.databinding.FragmentInspectionCheckListFlatTopBinding
import com.app.freightCyber.databinding.InspectionListItemBinding
import com.app.freightCyber.domain.model.dummy_data.InnerInspectionCheckList
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class InspectionCheckFlatTopFragment : BaseFragment<FragmentInspectionCheckListFlatTopBinding>() {

    private var data: String? = null
    private val viewModel: InspectionCheckFlatTopFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_inspection_check_list_flat_top
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        getData()
        setUpAdapter()
    }

    private fun getData() {
        data = arguments?.getString("FROM")
        when (data) {
            "FROM_FUEL" -> {
                binding.tvProfileText.text = "Post Inspection Details for FLAT TOP RIGID NO CRANE"
                binding.tvNext.text = "Complete Job"
            }

            else -> {
                binding.tvProfileText.text =
                    "Pre-Job Inspection Details for FLAT TOP RIGID NO CRANE"
                binding.tvNext.text = "Next"
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
                    when (data) {
                        "FROM_FUEL" -> {
                            HelperUtils.navigateWithSlideAnimations(
                                findNavController(),
                                R.id.jobSuccessFragment
                            )
                        }

                        else -> {
                            HelperUtils.navigateWithSlideAnimations(
                                findNavController(),
                                R.id.declarationConfirmationFragment
                            )
                        }
                    }

                }
            }
        })
    }

    private var Adapter: SimpleRecyclerViewAdapter<InnerInspectionCheckList, InspectionListItemBinding>? =
        null

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpAdapter() {
        Adapter = SimpleRecyclerViewAdapter(R.layout.inspection_list_item,
                BR.bean,
                SimpleRecyclerViewAdapter.SimpleCallback { v, m, pos ->
                    when (v.id) {
                        R.id.tvHeader -> {
                            val view: View = v?.parent as View
                            val csDetails = view.findViewById<ConstraintLayout>(R.id.csDetailFlat)
                            val tvHeader = view.findViewById<TextView>(R.id.tvHeader)
                            val imgUpDown = view.findViewById<ImageView>(R.id.imgUpDown)
                            if (csDetails.visibility == View.VISIBLE) {
                                hideViewWithAnimation(csDetails)
                                tvHeader.visibility = View.VISIBLE
                                imgUpDown.visibility = View.VISIBLE
                            } else {
                                showViewWithAnimation(csDetails)
                                tvHeader.visibility = View.GONE
                                imgUpDown.visibility = View.GONE
                            }
                        }
                    }
                }
            )
        binding.rvInspectionList.adapter = Adapter
        Adapter?.list = listData()
    }


    private fun hideViewWithAnimation(
        rvView: ConstraintLayout?,
    ) {
        val slideUpAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_up_animation)
        rvView?.startAnimation(slideUpAnimation)
        rvView?.visibility = View.GONE
    }

    private fun showViewWithAnimation(rvView: ConstraintLayout?) {
        val slideDownAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_down_animation)
        rvView?.startAnimation(slideDownAnimation)
        rvView?.visibility = View.VISIBLE
    }


    private fun listData(): ArrayList<InnerInspectionCheckList> {
        val list = ArrayList<InnerInspectionCheckList>()
        list.add(
            InnerInspectionCheckList(
                "Fluid Levels & Leaks",
                "Compliance status",
                "In good condition",
                "Issue found"
            )
        )
        list.add(
            InnerInspectionCheckList(
                "Water-Levels & Leaks",
                "Compliance status",
                "In good condition",
                "Issue found"
            )
        )

        list.add(
            InnerInspectionCheckList(
                "Oil-Levels & Leaks",
                "Compliance status",
                "In good condition",
                "Issue found"
            )
        )

        list.add(
            InnerInspectionCheckList(
                "Tyres & Wheels",
                "Compliance status",
                "In good condition",
                "Issue found"
            )
        )

        list.add(
            InnerInspectionCheckList(
                "Vehicle Lights & Signals",
                "Compliance status",
                "In good condition",
                "Issue found"
            )
        )

        list.add(
            InnerInspectionCheckList(
                "Mirrors & Visibility",
                "Compliance status",
                "In good condition",
                "Issue found"
            )
        )
        list.add(InnerInspectionCheckList(
            "Asset Status",
            "Fit For Use",
            "Yes",
            "No"))
        return list
    }
}