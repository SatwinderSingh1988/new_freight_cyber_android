package com.app.freightCyber.presentation.home_dashboard.job.asset_checklist.inspection

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.BR
import com.app.freightCyber.R
import com.app.freightCyber.domain.model.dummy_data.InnerInspectionCheckList
import com.app.freightCyber.databinding.FragmentInspectionCheckTautlinerBinding
import com.app.freightCyber.databinding.InspectionTautlinerListItemBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.recycler_view.SimpleRecyclerViewAdapter
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class InspectionCheckTautlinerFragment : BaseFragment<FragmentInspectionCheckTautlinerBinding>() {

    private val viewModel: InspectionCheckTautlinerFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_inspection_check_tautliner
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        setUpAdapter()
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    findNavController().popBackStack()
                }

                R.id.tvNext -> {
                    HelperUtils.navigateWithSlideAnimations(findNavController(), R.id.declarationConfirmationFragment)
                }
            }
        })
    }


    private var Adapter: SimpleRecyclerViewAdapter<InnerInspectionCheckList, InspectionTautlinerListItemBinding>? = null

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpAdapter() {
        Adapter =
            SimpleRecyclerViewAdapter(R.layout.inspection_tautliner_list_item,
                BR.bean,
                SimpleRecyclerViewAdapter.SimpleCallback { v, m, pos ->
                    when (v.id) {
                        R.id.tvHeader -> {
                            val view: View = v?.parent as View
                            val csDetails = view.findViewById<ConstraintLayout>(R.id.csDetailTautliner)
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

                        R.id.tvChecked -> {
                            val view: View = v?.parent as View
                            findViewId(view, 1)
                        }

                        R.id.tvIssue -> {
                            val view: View = v?.parent as View
                            findViewId(view, 2)
                        }

                        R.id.tvAssetNotFit -> {
                            val view: View = v?.parent as View
                            findViewId(view, 3)
                        }
                    }
                }
            )
        binding.rvInspectionList.adapter = Adapter
        Adapter?.list = authList()
    }

    private fun findViewId(view: View, button: Int) {
        val tvChecked = view.findViewById<TextView>(R.id.tvChecked)
        val tvIssue = view.findViewById<TextView>(R.id.tvIssue)
        val tvAssetNotFit = view.findViewById<TextView>(R.id.tvAssetNotFit)
        when(button){
            1 ->{
                handleTvChecked(tvChecked ,tvIssue , tvAssetNotFit)
            }
            2 ->{
                handleTvIssue(tvChecked ,tvIssue , tvAssetNotFit)
            }
            3 ->{
                handleTvAssetNotFit(tvChecked ,tvIssue , tvAssetNotFit)
            }
        }
    }

    private fun handleTvChecked(tvChecked: TextView?, tvIssue: TextView?, tvAssetNotFit: TextView?) {
        tvChecked?.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext() , R.color.green_progress2))
        tvIssue?.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext() , R.color.black_4))
        tvAssetNotFit?.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext() , R.color.black_4))
    }

    private fun handleTvIssue(tvChecked: TextView?, tvIssue: TextView?, tvAssetNotFit: TextView?) {
        tvChecked?.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext() , R.color.black_4))
        tvIssue?.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext() , R.color.dark_red))
        tvAssetNotFit?.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext() , R.color.black_4))
    }

    private fun handleTvAssetNotFit(tvChecked: TextView?, tvIssue: TextView?, tvAssetNotFit: TextView?) {
        tvChecked?.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext() , R.color.black_4))
        tvIssue?.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext() , R.color.black_4))
        tvAssetNotFit?.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext() , R.color.pink))
    }


    private fun hideViewWithAnimation(view: ConstraintLayout?) {
        val slideUpAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_up_animation)
        view?.startAnimation(slideUpAnimation)
        view?.visibility = View.GONE
    }

    private fun showViewWithAnimation(view: ConstraintLayout?) {
        val slideDownAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_down_animation)
        view?.startAnimation(slideDownAnimation)
        view?.visibility = View.VISIBLE
    }


    private fun authList(): ArrayList<InnerInspectionCheckList> {
        val list = ArrayList<InnerInspectionCheckList>()
        list.add(InnerInspectionCheckList("Fluid Levels & Leaks", "Compliance status", "In good condition", "Issue found"))
        list.add(InnerInspectionCheckList("Water-Level & Leaks", "Compliance status", "In good condition", "Issue found"))
        list.add(InnerInspectionCheckList("Tyres & Wheels", "Compliance status", "In good condition", "Issue found"))
        list.add(InnerInspectionCheckList("Vehicle Lights & Signals", "Compliance status", "In good condition", "Issue found"))
        list.add(InnerInspectionCheckList("Mirrors & Visibility", "Compliance status", "In good condition", "Issue found"))
        list.add(InnerInspectionCheckList("Safety Equipment", "Compliance status", "In good condition", "Issue found"))
        return list
    }

}