package com.app.freightCyber.presentation.auth.scan_driver_license

import android.content.Intent
import android.graphics.Bitmap
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.freightCyber.R
import com.app.freightCyber.databinding.ActivityLicenseVerificationBinding
import com.app.freightCyber.presentation.auth.personal_details.PersonalDetailActivity
import com.app.freightCyber.core.base.activity.BaseActivity
import com.app.freightCyber.core.base.view_model.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LicenseVerificationActivity : BaseActivity<ActivityLicenseVerificationBinding>() {

    private val viewModel: LicenseVerificationActivityVM by viewModels()

    companion object{
        var image : Bitmap? = null
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_license_verification
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView() {
        setStatusTextColor()
        initOnClick()
        if(image != null){
            binding.imgLicense.setImageBitmap(image)
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
                R.id.tvGoBack -> {
                    onBackPressedDispatcher.onBackPressed()
                }

                R.id.tvNext -> {
                val intent = Intent(this , PersonalDetailActivity::class.java)
                startActivity(intent)
                }
            }
        })
    }
}