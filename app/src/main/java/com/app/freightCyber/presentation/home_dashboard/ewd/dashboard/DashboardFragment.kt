package com.app.freightCyber.presentation.home_dashboard.ewd.dashboard

import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentDashboardBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {

    private var data: String? = null
    private val viewModel: DashboardFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_dashboard
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        getData()
    }

    private fun getData() {
         data = arguments?.getString("FROM")
        when(data){
            "CHANGE_STATUS_TO_REST" ->{
                binding.circularProgress.setCurrentProgress(70.0)
                binding.circularProgress.progressColor = ContextCompat.getColor(requireContext() , R.color.yellow2)
                binding.tvSoloWorking.text = getString(R.string.resting)
                binding.imgGreenDot.setColorFilter(ContextCompat.getColor(requireContext(), R.color.yellow2), android.graphics.PorterDuff.Mode.MULTIPLY)
            }
            else ->{
                binding.circularProgress.setCurrentProgress(70.0)
                binding.circularProgress.progressColor = ContextCompat.getColor(requireContext() , R.color.green_progress)
                binding.tvSoloWorking.text = getString(R.string.solo_working)
                binding.imgGreenDot.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green), android.graphics.PorterDuff.Mode.MULTIPLY)
            }
        }
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.tvEndShift -> {
                    findNavController().popBackStack(R.id.ewdFragment , false)
                }
                R.id.imgLogOff , R.id.tvLogOff -> {
                    HelperUtils.navigateWithSlideAnimations(findNavController() , R.id.loggingOffFirstFragment)
                }
                R.id.imgEWork , R.id.tvEWorkDiary -> {
                    HelperUtils.navigateWithSlideAnimations(findNavController() , R.id.addDiarySwitchFragment)
                }
                R.id.etDateComplete-> {
                    HelperUtils.openDatePickerDialog(requireContext() , binding.etDateComplete)
                }
                R.id.imgBack-> {
                   findNavController().popBackStack()
                }
                R.id.viewNonComplianceAlert-> {
                  HelperUtils.navigateWithSlideAnimations(findNavController() ,R.id.potentialNonComplianceFragment)
                }
            }
        })
    }
}