package com.app.freightCyber.presentation.auth.profile_complete

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.freightCyber.R
import com.app.freightCyber.databinding.ActivityProfileCompleteBinding
import com.app.freightCyber.core.base.activity.BaseActivity
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.presentation.home_dashboard.HomeActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileCompleteActivity : BaseActivity<ActivityProfileCompleteBinding>() {

    private val viewModel: ProfileCompleteActivityVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.activity_profile_complete
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

                R.id.tvContinue -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                   // finishAffinity()
                }

                R.id.tvViewProfile -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }
            }
        })
    }
}