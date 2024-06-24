package com.app.freightCyber.presentation.home_dashboard.ewd.two_up.two_up_dashboard

import android.content.res.ColorStateList
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentDashboardTwoUpBinding
import com.app.freightCyber.databinding.LogOfDialogBinding
import com.app.freightCyber.databinding.SwitchDriverDialogBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.dialog.BaseCustomDialog
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TwoUpDashboardFragment : BaseFragment<FragmentDashboardTwoUpBinding>() {

    private val viewModel: TwoUpDashboardFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_dashboard_two_up
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        binding.circularProgress.setCurrentProgress(70.0)
        showSwitchDialog()
        showLogOfDialog()
        val data = arguments?.getString("FROM")
        when(data){
            "SWITCH_DRIVER_BIO" ->{
                binding.tvDriver.text = "Secondary Driver"
                binding.driverDetail.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext() , R.color.dark_black2))
                binding.circularProgress.progressColor = ContextCompat.getColor(requireContext() , R.color.yellow2)
                binding.csDriverSwitchContent.visibility = View.VISIBLE
            }
            else ->{
                binding.tvDriver.text = "Primary Driver"
                binding.circularProgress.progressColor = ContextCompat.getColor(requireContext() , R.color.green_progress)
                binding.driverDetail.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext() , R.color.yellow))
                binding.csDriverSwitchContent.visibility = View.GONE
            }
        }
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.tvSeeDetails -> {
                    HelperUtils.navigateWithSlideAnimations(findNavController(),R.id.potentialNonComplianceFragment)
                }
                R.id.tvEndShift -> {
                    findNavController().popBackStack(R.id.ewdFragment , false)
                }
                R.id.imgBack -> {
                    findNavController().popBackStack()
                }
                R.id.imgLogOff , R.id.tvLogOff -> {
                    showLogOfDialog?.show()
                }

                R.id.imgSwitchDriver , R.id.tvSwitchDriver -> {
                    switchDriverDialog?.show()
                }

                R.id.imgEWork , R.id.tvEWorkDiary -> {
                    HelperUtils.navigateWithSlideAnimations(findNavController() , R.id.addDiarySwitchFragment)
                }
            }
        })
    }

    private var switchDriverDialog: BaseCustomDialog<SwitchDriverDialogBinding>? = null
    private fun showSwitchDialog() {
        switchDriverDialog =
            BaseCustomDialog(
                requireContext(),
                R.layout.switch_driver_dialog,
                BaseCustomDialog.Listener {
                    when (it.id) {
                        R.id.tvYes -> {
                            findNavController().navigate(R.id.switchDriverBioFragment)
                            switchDriverDialog?.dismiss()
                        }

                        R.id.tvNo -> {
                            switchDriverDialog?.dismiss()
                        }
                    }
                })
        switchDriverDialog?.setCancelable(false)
    }

    private var showLogOfDialog: BaseCustomDialog<LogOfDialogBinding>? = null
    private fun showLogOfDialog() {
        showLogOfDialog =
            BaseCustomDialog(
                requireContext(),
                R.layout.log_of_dialog,
                BaseCustomDialog.Listener {
                    when (it.id) {
                        R.id.tvYes -> {
                            findNavController().navigate(R.id.loggingOffFirstFragment)
                            showLogOfDialog?.dismiss()
                        }

                        R.id.tvNo -> {
                            showLogOfDialog?.dismiss()
                        }
                    }
                })
        showLogOfDialog?.setCancelable(false)
    }
}