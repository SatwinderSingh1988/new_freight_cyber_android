package com.app.freightCyber.presentation.auth.verify_otp

import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.app.freightCyber.R
import com.app.freightCyber.databinding.ActivityOtpSuccessBinding
import com.app.freightCyber.presentation.auth.authentication_method.AuthenticationActivity
import com.app.freightCyber.core.base.activity.BaseActivity
import com.app.freightCyber.core.base.view_model.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtpSuccessActivity : BaseActivity<ActivityOtpSuccessBinding>(){

    private val viewModel: VerifyOtpActivityVM by viewModels()


    override fun getLayoutResource(): Int {
        return R.layout.activity_otp_success
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView() {
        setStatusTextColor()
        initHandler()
    }

    private fun initHandler() {
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            val intent = Intent(this , AuthenticationActivity::class.java)
            startActivity(intent)
            finish()
        },2000)
    }

    private fun setStatusTextColor() {
        window.decorView.systemUiVisibility = 0
    }
}