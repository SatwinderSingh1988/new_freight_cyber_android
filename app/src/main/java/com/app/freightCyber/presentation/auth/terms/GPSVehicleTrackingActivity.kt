package com.app.freightCyber.presentation.auth.terms

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.freightCyber.R
import com.app.freightCyber.databinding.ActivityGpsTrackingBinding
import com.app.freightCyber.core.base.activity.BaseActivity
import com.app.freightCyber.core.base.view_model.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GPSVehicleTrackingActivity : BaseActivity<ActivityGpsTrackingBinding>() {

    private val viewModel: TermsAndConditionsActivityVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.activity_gps_tracking
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
                R.id.tvAccept -> {
                    startActivity(Intent(this , EnableLocationActivity::class.java))
                    finish()
                }
            }
        })
    }

    private fun setStatusTextColor() {
        window.decorView.systemUiVisibility = 0
    }
}