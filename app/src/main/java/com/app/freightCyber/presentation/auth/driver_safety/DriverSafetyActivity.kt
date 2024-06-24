package com.app.freightCyber.presentation.auth.driver_safety

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.freightCyber.R
import com.app.freightCyber.databinding.ActivityDriverSafetyBinding
import com.app.freightCyber.presentation.home_dashboard.driver_profile.transport_operators.TransportOperatorFragment
import com.app.freightCyber.core.base.activity.BaseActivity
import com.app.freightCyber.core.base.view_model.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DriverSafetyActivity : BaseActivity<ActivityDriverSafetyBinding>() {

    private val viewModel: DriverSafetyActivityVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.activity_driver_safety
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
                R.id.tvNext -> {
                     val intent = Intent(this, TransportOperatorFragment::class.java)
                    startActivity(intent)
                }
            }
        })
    }

    private fun setStatusTextColor() {
        window.decorView.systemUiVisibility = 0
    }
}