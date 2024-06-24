
package com.app.freightCyber.presentation.home_dashboard.job.asset_checklist.inspection


import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentInspectionCheckListBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InspectionCheckListFragment : BaseFragment<FragmentInspectionCheckListBinding>() {

    private var data: String? = null
    private val viewModel: InspectionCheckListFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_inspection_check_list
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view : View) {
        initOnClick()
        data = arguments?.getString("FROM")
        when(data){
            "TAUTLINER RIGID NO CRANE"->{
                binding.viewTrailerRego.visibility = View.VISIBLE
                binding.tv10.visibility = View.VISIBLE
                binding.tvTrailerRego.visibility = View.VISIBLE
                binding.tvProfileText.text = "Pre-Job Inspection Details for TAUTLINER RIGID NO CRANE"
            }
            "FLAT TOP RIGID NO CRANE"->{
                binding.viewTrailerRego.visibility = View.GONE
                binding.tv10.visibility = View.GONE
                binding.tvTrailerRego.visibility = View.GONE
                binding.tvProfileText.text = "Pre-Job Inspection Details for FLAT TOP RIGID NO CRANE"
            }
            else->{}
        }
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                  findNavController().popBackStack()
                }

                R.id.tvNext -> {
                    when(data){
                        "TAUTLINER RIGID NO CRANE"->{
                            HelperUtils.navigateWithSlideAnimations(findNavController() , R.id.inspectionCheckTautlinerFragment)
                        }
                        "FLAT TOP RIGID NO CRANE"->{
                            HelperUtils.navigateWithSlideAnimations(findNavController() , R.id.inspectionCheckListTwoFragment)
                        }
                        else->{}
                    }
                }
            }
        })
    }

}