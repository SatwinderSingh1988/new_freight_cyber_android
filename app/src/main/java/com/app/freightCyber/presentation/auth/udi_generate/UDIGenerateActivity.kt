package com.app.freightCyber.presentation.auth.udi_generate

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.freightCyber.R
import com.app.freightCyber.databinding.ActivityUdiGenerateBinding
import com.app.freightCyber.presentation.auth.emergency_details.EmergencyActivity
import com.app.freightCyber.core.base.activity.BaseActivity
import com.app.freightCyber.core.base.view_model.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UDIGenerateActivity : BaseActivity<ActivityUdiGenerateBinding>() {

    private val viewModel: UDIGenerateActivityVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.activity_udi_generate
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView() {
        setStatusTextColor()
        initOnClick()
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
                R.id.tvNext -> {
                       val intent = Intent(this, EmergencyActivity::class.java)
                       startActivity(intent)
                }
                R.id.tvGoBack -> {
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }
}
