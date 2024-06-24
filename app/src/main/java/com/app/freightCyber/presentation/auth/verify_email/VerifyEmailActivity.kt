package com.app.freightCyber.presentation.auth.verify_email

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
import com.app.freightCyber.databinding.ActivityVerifyEmailBinding
import com.app.freightCyber.domain.model.request.GeneratePasscodeRequest
import com.app.freightCyber.presentation.auth.verify_otp.VerifyOtpActivity
import com.app.freightCyber.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VerifyEmailActivity : BaseActivity<ActivityVerifyEmailBinding>() {

    private var fromScreen: String? = null
    private val viewModel: VerifyEmailActivityVM by viewModels()
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun getLayoutResource(): Int {
        return R.layout.activity_verify_email
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView() {
        setStatusTextColor()
        initOnClick()
        getData()
        initObservers()
    }

    private fun getData() {
        fromScreen = intent.getStringExtra("FROM")
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.passcodeResult.collect { result ->
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
                            viewModel.resetUpdateGeneratePasscodeResult()
                        }

                        null -> {}
                    }
                }
            }
        }
    }

    private fun setStatusTextColor() {
        window.decorView.systemUiVisibility = 0
    }

    private fun initOnClick() {
        viewModel.onClick.observe(this, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    onBackPressedDispatcher.onBackPressed()
                }

                R.id.tvVerifyAccount -> {
                     checkValidation()
                }
            }
        })
    }

    private fun checkValidation() {
        val emailText = binding.etEmail.text.toString()

        if (TextUtils.isEmpty(emailText)) {
            showToast(getString(R.string.Please_enter_your_email_address))
            return
        }

        if (!emailText.matches(emailPattern.toRegex())) {
            showToast(getString(R.string.invalid_email))
            return
        }

        val data = GeneratePasscodeRequest(binding.etEmail.text.toString())
        viewModel.generatePasscode(data)

    }

    private fun goToNextScreen() {
        val fromValue = when (fromScreen) {
            "LOGIN" -> "LOGIN"
            else -> "EMAIL_VERIFICATION"
        }
        val intent = Intent(this, VerifyOtpActivity::class.java)
            .apply {
                putExtra("FROM", fromValue)
                putExtra("EMAIL", binding.etEmail.text.toString())
            }
        startActivity(intent)
    }
}