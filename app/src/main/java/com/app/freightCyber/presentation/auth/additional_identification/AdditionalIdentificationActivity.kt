package com.app.freightCyber.presentation.auth.additional_identification

import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.EditText
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.app.freightCyber.R
import com.app.freightCyber.databinding.ActivityAdditionalIdentiBinding
import com.app.freightCyber.presentation.auth.profile_complete.ProfileCompleteActivity
import com.app.freightCyber.core.base.activity.BaseActivity
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.domain.model.request.AdditionalIdentificationRequest
import com.app.freightCyber.utils.HelperUtils
import com.app.freightCyber.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AdditionalIdentificationActivity : BaseActivity<ActivityAdditionalIdentiBinding>() {

    private var countryOfIssueList: List<String>? = null
    private var idTypeList: List<String>? = null
    private val viewModel: AdditionalIdentificationActivityVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.activity_additional_identi
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView() {
        setStatusTextColor()
        initOnClick()
        idTypeList = listOf("Item1", "Item2", "Item3")
        countryOfIssueList = listOf("India", "United State", "Canada")
        addTextWatcher(binding.etIssue)
        addTextWatcher(binding.etExpiry)
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.addIdentificationResult.collect { result ->
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
                            viewModel.resetUpdateAdditionalResult()
                        }

                        null -> {}
                    }
                }
            }
        }
    }

    private fun addTextWatcher(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            var currentLength = 0
            var isDeleting = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                currentLength = s?.length ?: 0
                isDeleting = count > after
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val input = s.toString()
                if (input.length == 2 && currentLength < input.length && !isDeleting) {
                    editText.setText("$input/")
                    editText.setSelection(input.length + 1)
                }
            }
        })
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

                R.id.etCountryIssue -> {
                    if (countryOfIssueList != null) {
                        HelperUtils.showPopupMenu(this, binding.etCountryIssue, countryOfIssueList!!)
                    }
                }

                R.id.etAdditionalId -> {
                    if (idTypeList != null) {
                        HelperUtils.showPopupMenu(this, binding.etAdditionalId, idTypeList!!)
                    }
                }

                R.id.tvSkip -> {
                    val intent = Intent(this, ProfileCompleteActivity::class.java)
                    startActivity(intent)
                }

                R.id.tvNext -> {
                  checkValidation()
                }
            }
        })
    }

    private fun checkValidation() {
        if (TextUtils.isEmpty(binding.etAdditionalId.text.toString())) {
            showToast(getString(R.string.please_fill_all_the_details))
            return
        }
        if (TextUtils.isEmpty(binding.etPasssportNumber.text.toString())) {
            showToast(getString(R.string.please_fill_all_the_details))
            return
        }
        if (TextUtils.isEmpty(binding.etCountryIssue.text.toString())) {
            showToast(getString(R.string.please_fill_all_the_details))
            return
        }
        if (TextUtils.isEmpty(binding.etIssue.text.toString())) {
            showToast(getString(R.string.please_fill_all_the_details))
            return
        }
        if (TextUtils.isEmpty(binding.etExpiry.text.toString())) {
            showToast(getString(R.string.please_fill_all_the_details))
            return
        }

        viewModel.addAdditionalIdentification(
            AdditionalIdentificationRequest(
                expiry = binding.etExpiry.text.toString(),
                id_type =  binding.etAdditionalId.text.toString() ,
                issue = binding.etIssue.text.toString() ,
                passport_number = binding.etPasssportNumber.text.toString() ,
                country_of_issue = binding.etCountryIssue.text.toString()
            )
        )
    }

    private fun setStatusTextColor() {
        window.decorView.systemUiVisibility = 0
    }

    private fun goToNextScreen(){
        val intent = Intent(this, ProfileCompleteActivity::class.java)
        startActivity(intent)
    }
}
