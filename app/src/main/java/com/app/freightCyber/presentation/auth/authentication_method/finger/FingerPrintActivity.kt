package com.app.freightCyber.presentation.auth.authentication_method.finger

import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.app.freightCyber.R
import com.app.freightCyber.databinding.ActivityFingerprintBinding
import com.app.freightCyber.presentation.auth.authentication_method.facial.FacialActivity
import com.app.freightCyber.core.base.activity.BaseActivity
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.utils.HelperUtils.setTint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FingerPrintActivity : BaseActivity<ActivityFingerprintBinding>() {

    private val viewModel: FingerPrintActivityVM by viewModels()

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun getLayoutResource(): Int {
        return R.layout.activity_fingerprint
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView() {
        setStatusTextColor()
        initOnClick()
    }

    private fun initOnClick() {
        viewModel.onClick.observe(this, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    onBackPressedDispatcher.onBackPressed()
                }

                R.id.imgFinger -> {
                  //  biometricLogin()
                    val intent = Intent(this@FingerPrintActivity, FacialActivity::class.java)
                    startActivity(intent)
                }
            }
        })
    }

    private fun setStatusTextColor() {
        window.decorView.systemUiVisibility = 0
    }


    private fun biometricLogin() {
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {}
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> showToast("This device doesn't support biometric authentication")
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> showToast("Biometric authentication is currently unavailable")
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> showToast("Please Enable Biometric authentication First ")
            BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> { showToast("Biometric authentication security required") }
            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> { showToast("Biometric authentication unsupported") }
            BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> { showToast("Biometric authentication unknown") }
        }

        val executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                //    showToast("Register error: $errString")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    binding.imgFinger.setTint(R.color.green)
                //    showToast("Register succeeded!")
                    Handler(Looper.getMainLooper()).postDelayed({
                        val intent = Intent(this@FingerPrintActivity, FacialActivity::class.java)
                        startActivity(intent)
                        binding.imgFinger.setTint(R.color.grey)
                    }, 2000)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    binding.imgFinger.setTint(R.color.red)
                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.imgFinger.setTint(R.color.grey)
                    }, 2000)
                    showToast("Authentication failed.")
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder().setTitle("Biometric Authentication")
            .setSubtitle("Register your biometric").setNegativeButtonText("Cancel").build()

        biometricPrompt.authenticate(promptInfo)

    }
}
