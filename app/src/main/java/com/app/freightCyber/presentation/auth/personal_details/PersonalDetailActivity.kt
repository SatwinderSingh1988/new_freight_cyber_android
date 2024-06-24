package com.app.freightCyber.presentation.auth.personal_details

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.app.freightCyber.R
import com.app.freightCyber.databinding.ActivityPersonalDetailBinding
import com.app.freightCyber.presentation.auth.license.LicenseDetailActivity
import com.app.freightCyber.core.base.activity.BaseActivity
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.domain.model.request.DriverProfileRequest
import com.app.freightCyber.utils.HelperUtils
import com.app.freightCyber.utils.NetworkResult
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PersonalDetailActivity : BaseActivity<ActivityPersonalDetailBinding>() {

    private var titleList: List<String>? = null
    private val viewModel: PersonalDetailActivityVM by viewModels()

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                val fileUri = data?.data!!
                binding.imgProfile.setImageURI(fileUri)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                showToast(ImagePicker.getError(data))
            } else {
                showToast("Task Cancelled")
            }
        }

    override fun getLayoutResource(): Int {
        return R.layout.activity_personal_detail
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView() {
        setStatusTextColor()
        initOnClick()
        titleList = listOf("Prof", "Dr", "Mr.", "Mrs.", "Miss", "Ms.")
        getSharedPrefData()
        initObservers()
    }

    private fun getSharedPrefData() {
        val email = sharedPrefManager.getCurrentUser()?.data?.user?.email
        if(email != null){
            binding.etEmail.setText(email)
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.updateDriverProfileResult.collect { result ->
                        when (result) {
                            is NetworkResult.Error -> {
                                hideNewLoading()
                                showToast(result.exception.message)
                            }

                            is NetworkResult.Loading -> {
                                showNewLoading()
                            }

                            is NetworkResult.Success -> {
                                hideNewLoading()
                                showToast(result.data.message)
                                goToNextScreen()
                                viewModel.resetUpdateDriverProfileResult()
                            }

                            null -> {}
                        }
                    }
                }
            }
        }
    }

    private fun goToNextScreen() {
        val intent = Intent(this, LicenseDetailActivity::class.java)
        startActivity(intent)
    }

    private fun initOnClick() {
        viewModel.onClick.observe(this, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    onBackPressedDispatcher.onBackPressed()
                }

                R.id.etDateOfBirth -> {
                    HelperUtils.openDatePickerDialog(this ,binding.etDateOfBirth)
                }

                R.id.etTitle -> {
                    if(titleList != null){
                        HelperUtils.showPopupMenu(this ,binding.etTitle , titleList!!)
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

                R.id.tvNext -> {
                    checkValidation()
                }
                R.id.tvGoBack ->{
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    private fun checkValidation() {
        if (TextUtils.isEmpty(binding.etTitle.text.toString())) {
            showToast(getString(R.string.please_fill_all_the_details))
            return
        }
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

    private fun setStatusTextColor() {
        window.decorView.systemUiVisibility = 0
    }
}