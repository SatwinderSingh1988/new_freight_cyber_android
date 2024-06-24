package com.app.freightCyber.presentation.auth.terms

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.freightCyber.R
import com.app.freightCyber.databinding.ActivityLocationBinding
import com.app.freightCyber.core.base.activity.BaseActivity
import com.app.freightCyber.core.base.view_model.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EnableLocationActivity : BaseActivity<ActivityLocationBinding>() {

    private val viewModel: TermsAndConditionsActivityVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.activity_location
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
                    startActivity(Intent(this , ComplianceWithLawsActivity::class.java))
                    finish()
                }
            }
        })
    }

    private fun setStatusTextColor() {
        window.decorView.systemUiVisibility = 0
    }
}