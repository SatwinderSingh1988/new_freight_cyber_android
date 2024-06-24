package com.app.freightCyber.presentation.auth.phone_verify_success

import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.app.freightCyber.R
import com.app.freightCyber.databinding.ActivityPhoneVerifySuccessBinding
import com.app.freightCyber.presentation.auth.verify_email.VerifyEmailActivity
import com.app.freightCyber.core.base.activity.BaseActivity
import com.app.freightCyber.core.base.view_model.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PhoneVerifySuccessActivity : BaseActivity<ActivityPhoneVerifySuccessBinding>() {

    private val viewModel: PhoneVerifySuccessActivityVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.activity_phone_verify_success
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
            val intent = Intent(this , VerifyEmailActivity::class.java)
            startActivity(intent)
            finish()
        },2000)
    }

    private fun setStatusTextColor() {
        window.decorView.systemUiVisibility = 0
    }

}