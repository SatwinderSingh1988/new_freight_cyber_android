package com.app.freightCyber.presentation.auth.emergency_details


import android.content.Intent
import android.text.TextUtils
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.app.freightCyber.R
import com.app.freightCyber.core.base.activity.BaseActivity
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.databinding.ActivityEmergencyBinding
import com.app.freightCyber.domain.model.request.EmergencyContactRequest
import com.app.freightCyber.presentation.auth.additional_identification.AdditionalIdentificationActivity
import com.app.freightCyber.utils.HelperUtils
import com.app.freightCyber.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class EmergencyActivity : BaseActivity<ActivityEmergencyBinding>() {

    private val viewModel: EmergencyActivityVM by viewModels()
    private var relationshipList: List<String>? = null

    override fun getLayoutResource(): Int {
        return R.layout.activity_emergency
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView() {
        setStatusTextColor()
        initOnClick()
        relationshipList = listOf("relation1", "relation2", "relation3")
        initObserver()
    }

    private fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.emergencyResult.collect { result ->
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
                            viewModel.resetUpdateEmergencyResult()
                        }

                        null -> {}
                    }
                }
            }
        }
    }

    private fun goToNextScreen() {
        val intent = Intent(this, AdditionalIdentificationActivity::class.java)
        startActivity(intent)
    }


    private fun initOnClick() {
        viewModel.onClick.observe(this, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    onBackPressedDispatcher.onBackPressed()
                }

                R.id.tvGoBack -> {
                    onBackPressedDispatcher.onBackPressed()
                }

                R.id.etRelationToUser -> {
                    if (relationshipList != null) {
                        HelperUtils.showPopupMenu(
                            this,
                            binding.etRelationToUser,
                            relationshipList!!
                        )
                    }
                }

                R.id.tvNext -> {
                   // goToNextScreen()
                     checkValidation()
                }
            }
        })
    }

    private fun checkValidation() {
        if (TextUtils.isEmpty(binding.etContactName.text.toString())) {
            showToast(getString(R.string.please_fill_all_the_details))
            return
        }
        if (TextUtils.isEmpty(binding.etRelationToUser.text.toString())) {
            showToast(getString(R.string.please_fill_all_the_details))
            return
        }
        if (TextUtils.isEmpty(binding.etPhoneNumber.text.toString())) {
            showToast(getString(R.string.please_fill_all_the_details))
            return
        }
        if (TextUtils.isEmpty(binding.etEmail.text.toString())) {
            showToast(getString(R.string.please_fill_all_the_details))
            return
        }

        viewModel.emergencyContact(
            EmergencyContactRequest(emerg_contact_full_name = binding.etContactName.text.toString(),
               emerg_contact_relationship =  binding.etRelationToUser.text.toString(),
                emerg_contact_full_phone = binding.etPhoneNumber.text.toString(),
                emerg_contact_email = binding.etEmail.text.toString()
            )
        )
    }

    private fun setStatusTextColor() {
        window.decorView.systemUiVisibility = 0
    }
}