package com.app.freightCyber.presentation.home_dashboard.settings.profile_setting

import android.app.Activity
import android.net.Uri
import android.text.TextUtils
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.core.base.dialog.BaseCustomDialog
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.data.source.local.prefrences.SharedPrefManager.KEY.USER_NAME
import com.app.freightCyber.databinding.FragmentProfileSettingsBinding
import com.app.freightCyber.databinding.RemovePhotoDialogDesignBinding
import com.app.freightCyber.domain.model.request.DriverProfileRequest
import com.app.freightCyber.domain.model.response.DriverProfileData
import com.app.freightCyber.utils.HelperUtils
import com.app.freightCyber.utils.NetworkResult
import com.app.freightCyber.utils.showToast
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProfileSettingsFragment : BaseFragment<FragmentProfileSettingsBinding>() {

    private var fileUri: Uri? = null
    private val viewModel: ProfileSettingsFragmentVM by viewModels()

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                fileUri = data?.data!!
                binding.imgProfile.setImageURI(fileUri)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                showToast(ImagePicker.getError(data))
            } else {
                showToast("Task Cancelled")
            }
        }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_profile_settings
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        showRemoveDialog()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.driverProfileResult.collect { result ->
                        when (result) {
                            is NetworkResult.Error -> {
                                hideNewLoading()
                                result.exception.message?.let { showToast(it) }
                            }

                            is NetworkResult.Loading -> {
                                showNewLoading()
                            }

                            is NetworkResult.Success -> {
                                hideNewLoading()
                                result.data.message?.let { showToast(it) }
                                binding.bean = result.data
                                viewModel.resetAndUpdateDriverProfile()
                            }

                            null -> {}
                        }
                    }
                }
                launch {
                    viewModel.updateDriverProfileResult.collect { result ->
                        when (result) {
                            is NetworkResult.Error -> {
                                hideNewLoading()
                                result.exception.message?.let { showToast(it) }
                            }

                            is NetworkResult.Loading -> {
                                showNewLoading()
                            }

                            is NetworkResult.Success -> {
                                hideNewLoading()
                                result.data.message?.let { showToast(it) }
                                updateSharedPrefUserData(result.data.data)
                            }
                            null -> {}
                        }
                    }
                }
            }
        }
    }

    private fun updateSharedPrefUserData(data: DriverProfileData?) {
        sharedPrefManager.updateCurrentUser(newValue = "${data?.first_name} ${data?.last_name}" , key = USER_NAME)
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    findNavController().popBackStack()
                }

                R.id.etDateOfBirth -> {
                    HelperUtils.openDatePickerDialog(requireContext(), binding.etDateOfBirth)
                }

                R.id.tvNext -> {
                    checkValidation()
                }

                R.id.tvRemovePhoto -> {
                    if (fileUri != null) {
                        removeDialog?.show()
                    } else {
                        showToast("please add your profile first.")
                    }
                }

                R.id.imgProfile, R.id.tvUploadPhoto -> {
                    ImagePicker.with(this)
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .createIntent { intent ->
                            startForProfileImageResult.launch(intent)
                        }
                }
            }
        })
    }

    private fun checkValidation() {
        if (TextUtils.isEmpty(binding.etFirstName.text.toString())) {
            showToast(getString(R.string.please_fill_all_the_details))
            return
        }
        if (TextUtils.isEmpty(binding.etLastName.text.toString())) {
            showToast(getString(R.string.please_fill_all_the_details))
            return
        }
        if (TextUtils.isEmpty(binding.etDateOfBirth.text.toString())) {
            showToast(getString(R.string.please_fill_all_the_details))
            return
        }

        viewModel.updateDriverProfile(
            DriverProfileRequest(title = binding.etTitle.text.toString(),
                first_name =  binding.etFirstName.text.toString(),
                last_name = binding.etLastName.text.toString(),
                date_of_birth = binding.etDateOfBirth.text.toString()
            )
        )
    }

    private var removeDialog: BaseCustomDialog<RemovePhotoDialogDesignBinding>? = null
    private fun showRemoveDialog() {
        removeDialog =
            BaseCustomDialog(
                requireContext(),
                R.layout.remove_photo_dialog_design,
                BaseCustomDialog.Listener {
                    when (it.id) {
                        R.id.tvYes -> {
                            binding.imgProfile.setImageResource(R.drawable.img_profile)
                            fileUri = null
                            removeDialog?.dismiss()
                        }

                        R.id.tvNo -> {
                            removeDialog?.dismiss()
                        }
                    }
                })
        removeDialog?.setCancelable(false)
    }

}