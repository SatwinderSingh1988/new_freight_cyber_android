package com.app.freightCyber.presentation.auth.verify_otp

import android.content.Intent
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.app.freightCyber.R
import com.app.freightCyber.core.base.activity.BaseActivity
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.databinding.ActivityVerifyOtpBinding
import com.app.freightCyber.domain.model.request.LoginRequest
import com.app.freightCyber.domain.model.request.SignUpRequest
import com.app.freightCyber.presentation.auth.authentication_method.AuthenticationActivity
import com.app.freightCyber.presentation.auth.authentication_method.AuthenticationCompleteActivity
import com.app.freightCyber.presentation.auth.phone_verify_success.PhoneVerifySuccessActivity
import com.app.freightCyber.presentation.home_dashboard.HomeActivity
import com.app.freightCyber.utils.NetworkResult
import com.app.freightCyber.utils.getDrawableCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VerifyOtpActivity : BaseActivity<ActivityVerifyOtpBinding>(), View.OnKeyListener {

    private var email: String = ""
    private var otpText: String = ""
    private var data: String? = null
    private val viewModel: VerifyOtpActivityVM by viewModels()
    private var countDownTimer: CountDownTimer? = null


    override fun getLayoutResource(): Int {
        return R.layout.activity_verify_otp
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView() {
        setStatusTextColor()
        initOnClick()
        showSoftKeyboard(binding.et1)
        getData()
        setUpTextWatcherOrListener()
        initObservers()
        startTimer()
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(35000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                val text = "Resend code in: $secondsRemaining s"
                binding.tvResendOtp.text = text
            }

            override fun onFinish() {
                binding.tvResendOtp.text = "Resend Otp"
            }
        }.start()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.loginResult.collect { loginResult ->
                        when (loginResult) {
                            is NetworkResult.Error -> {
                                hideNewLoading()
                                showToast(loginResult.exception.message)
                            }

                            is NetworkResult.Loading -> {
                                showNewLoading()
                            }

                            is NetworkResult.Success -> {
                                hideNewLoading()
                                showToast(loginResult.data.message)
                                sharedPrefManager.saveUser(loginResult.data)
                                sharedPrefManager.saveToken(loginResult.data.data?.access_token)
                                startActivityAndFinish(HomeActivity::class.java)
                                viewModel.resetUpdateLoginResult()
                            }

                            null -> {}
                        }
                    }
                }
                launch {
                    viewModel.signupResult.collect { signupResult ->
                        when (signupResult) {
                            is NetworkResult.Error -> {
                                hideNewLoading()
                                showToast(signupResult.exception.message)
                            }

                            is NetworkResult.Loading -> {
                                showNewLoading()
                            }

                            is NetworkResult.Success -> {
                                hideNewLoading()
                                showToast(signupResult.data.message)
                                sharedPrefManager.saveUser(signupResult.data)
                                sharedPrefManager.saveToken(signupResult.data.data?.access_token)
                                startActivityAndFinish(AuthenticationActivity::class.java)
                                viewModel.resetUpdateSignUpResult()
                            }

                            null -> {}
                        }
                    }
                }
            }
        }
    }

    private fun startActivityAndFinish(destination: Class<*>) {
        val intent = Intent(this@VerifyOtpActivity, destination)
        startActivity(intent)
        finish()
    }

    private fun setUpTextWatcherOrListener() {
        binding.et1.addTextChangedListener(mTextWatcher)
        binding.et2.addTextChangedListener(mTextWatcher)
        binding.et3.addTextChangedListener(mTextWatcher)
        binding.et4.addTextChangedListener(mTextWatcher)
        binding.et5.addTextChangedListener(mTextWatcher)
        binding.et6.addTextChangedListener(mTextWatcher)

        binding.et2.setOnKeyListener(this)
        binding.et3.setOnKeyListener(this)
        binding.et4.setOnKeyListener(this)
        binding.et5.setOnKeyListener(this)
        binding.et6.setOnKeyListener(this)
    }

    private fun getData() {
        data = intent?.extras?.getString("FROM") ?: return
        email = intent?.extras?.getString("EMAIL").toString()
        when (data) {
            "SIGNUP_SCREEN" -> {
                binding.tvVerify.text = getString(R.string.verify_your_phone_number)
                binding.tvPleaseEnter.text =
                    getString(R.string.please_enter_the_verification_code_sent_to_your_mobile_number_1_0123456789)
                binding.tvResendOtp.visibility = View.VISIBLE
                binding.tvVerifyAccount.text = getString(R.string.verify_phone_number)
                binding.tvVerify2.visibility = View.GONE
            }

            "AUTHENTICATION_SCREEN" -> {
                binding.tvVerify.text = getString(R.string.setup_your_passcode)
                binding.tvPleaseEnter.text =
                    getString(R.string.set_up_a_passcode_in_case_biometric_methods_are_unavailable_enter_a_4_digit_pin_below)
                binding.tvResendOtp.visibility = View.GONE
                binding.tvVerifyAccount.text = getString(R.string.save_pin)
                binding.tvVerify2.visibility = View.VISIBLE
            }

            "LOGIN" -> {
                binding.tvVerify.text = getString(R.string.login_with_passcode)
                binding.tvPleaseEnter.text = getString(R.string.enter_your_passcode_to_login)
                binding.tvResendOtp.visibility = View.GONE
                binding.tvVerifyAccount.text = getString(R.string.login)
                binding.tvVerify2.visibility = View.GONE
            }

            "EMAIL_VERIFICATION" -> {
                binding.tvVerify.text = getString(R.string.verify_your_email_address)
                binding.tvPleaseEnter.text =
                    getString(R.string.please_enter_the_verification_code_sent_to_your_email_admin_gmail_com)
                binding.tvResendOtp.visibility = View.VISIBLE
                binding.tvVerifyAccount.text = getString(R.string.submit)
                binding.tvVerify2.visibility = View.GONE
            }

            else -> {}
        }
    }

    private val mTextWatcher = object : TextWatcher {
        override fun afterTextChanged(et: Editable?) {
            when {
                et === binding.et1.editableText -> {
                    if (binding.et1.isFocused) binding.et1.background =
                        getDrawableCompat(R.drawable.edit_text_background)
                    if (binding.et1.text?.isNotEmpty() == true) binding.et2.requestFocus()
                    else binding.et1.background = getDrawableCompat(R.drawable.otp_selected_view)
                }

                et === binding.et2.editableText -> {
                    if (binding.et2.isFocused) binding.et2.background =
                        getDrawableCompat(R.drawable.edit_text_background)
                    if (binding.et2.text?.isNotEmpty() == true) binding.et3.requestFocus()
                    else binding.et2.background = getDrawableCompat(R.drawable.otp_selected_view)
                }

                et === binding.et3.editableText -> {
                    if (binding.et3.isFocused) binding.et3.background =
                        getDrawableCompat(R.drawable.edit_text_background)
                    if (binding.et3.text?.isNotEmpty() == true) binding.et4.requestFocus()
                    else binding.et3.background = getDrawableCompat(R.drawable.otp_selected_view)
                }

                et === binding.et4.editableText -> {
                    if (binding.et4.isFocused) binding.et4.background =
                        getDrawableCompat(R.drawable.edit_text_background)
                    if (binding.et4.text?.isNotEmpty() == true) binding.et5.requestFocus()
                    else binding.et4.background = getDrawableCompat(R.drawable.otp_selected_view)
                }

                et === binding.et5.editableText -> {
                    if (binding.et5.isFocused) binding.et5.background =
                        getDrawableCompat(R.drawable.edit_text_background)
                    if (binding.et5.text?.isNotEmpty() == true) binding.et6.requestFocus()
                    else binding.et5.background = getDrawableCompat(R.drawable.otp_selected_view)
                }

                et === binding.et6.editableText -> {
                    if (binding.et6.isFocused) binding.et6.background =
                        getDrawableCompat(R.drawable.edit_text_background)
                    if (binding.et6.text?.isEmpty() == true) binding.et6.background =
                        getDrawableCompat(R.drawable.otp_selected_view)
                }
            }
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    }


    private fun verifyOtp(): Boolean {
        if (binding.et1.text.toString().isNotEmpty() ||
            binding.et2.text.toString().isNotEmpty() ||
            binding.et3.text.toString().isNotEmpty() ||
            binding.et4.text.toString().isNotEmpty() ||
            binding.et5.text.toString().isNotEmpty() ||
            binding.et6.text.toString().isNotEmpty()
        ) {
            val otpStringBuilder = StringBuilder()
            otpStringBuilder.append(binding.et1.text)
            otpStringBuilder.append(binding.et2.text)
            otpStringBuilder.append(binding.et3.text)
            otpStringBuilder.append(binding.et4.text)
            otpStringBuilder.append(binding.et5.text)
            otpStringBuilder.append(binding.et6.text)
            otpText = otpStringBuilder.toString()
            return otpText.length == 6
        } else {
            return false
        }
    }

    private fun initOnClick() {
        viewModel.onClick.observe(this, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    onBackPressedDispatcher.onBackPressed()
                }

                R.id.tvVerifyAccount -> {
                    if (verifyOtp()) {
                        when (data) {
                            "SIGNUP_SCREEN" -> {
                                val intent = Intent(
                                    this@VerifyOtpActivity,
                                    PhoneVerifySuccessActivity::class.java
                                )
                                startActivity(intent)
                            }

                            "AUTHENTICATION_SCREEN" -> {
                                val intent = Intent(
                                    this@VerifyOtpActivity,
                                    AuthenticationCompleteActivity::class.java
                                )
                                startActivity(intent)
                            }

                            "LOGIN" -> {
                                // startActivityAndFinish(HomeActivity::class.java)
                                viewModel.login(LoginRequest(email, otpText))
                            }

                            "EMAIL_VERIFICATION" -> {
                                // startActivityAndFinish(AuthenticationActivity::class.java)
                                viewModel.signup(SignUpRequest(email, otpText))
                                Log.d("sdfsdfsfsfdfsd:value", "$email :$otpText")

                            }

                            else -> {
                                showToast("null data")
                            }
                        }
                    } else {
                        when (data) {
                            "SIGNUP_SCREEN" -> {
                                showToast("Please enter the verification code")
                            }

                            "AUTHENTICATION_SCREEN" -> {
                                showToast("Please set up your passcode")
                            }

                            "LOGIN" -> {
                                showToast("Please enter the passcode")
                            }

                            "EMAIL_VERIFICATION" -> {
                                showToast("Please enter the verification code")
                            }

                            else -> {}
                        }
                    }
                }
            }
        })
    }

    private fun setStatusTextColor() {
        window.decorView.systemUiVisibility = 0
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.action != KeyEvent.ACTION_DOWN) return false
        val currentEditText = v as? EditText ?: return false
        when (keyCode) {
            KeyEvent.KEYCODE_DEL -> {
                if (currentEditText.text.isEmpty() && currentEditText != binding.et1) {
                    val previousEditText = getPreviousEditText(currentEditText)
                    previousEditText?.text = null
                    previousEditText?.requestFocus()
                    return true
                }
            }

            KeyEvent.KEYCODE_BACK -> return false
        }
        return false
    }

    private fun getPreviousEditText(currentEditText: EditText): EditText? {
        return when (currentEditText) {
            binding.et2 -> binding.et1
            binding.et3 -> binding.et2
            binding.et4 -> binding.et3
            binding.et5 -> binding.et4
            binding.et6 -> binding.et5
            else -> null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }

}