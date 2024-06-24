package com.app.freightCyber.presentation.auth.splash

import android.content.Intent
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.freightCyber.R
import com.app.freightCyber.databinding.ActivitySecondSplashBinding
import com.app.freightCyber.presentation.auth.login.LoginActivity
import com.app.freightCyber.presentation.auth.signup.SignUpActivity
import com.app.freightCyber.core.base.activity.BaseActivity
import com.app.freightCyber.core.base.view_model.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashSecondActivity : BaseActivity<ActivitySecondSplashBinding>() {

    private val viewModel : SplashFirstActivityVM by viewModels()

    override fun getLayoutResource(): Int {
      return R.layout.activity_second_splash
    }

    override fun getViewModel(): BaseViewModel {
     return viewModel
    }

    override fun onCreateView() {
        initOnClick()
        setFullScreen()
    }

    private fun setFullScreen() {
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    private fun initOnClick() {
        viewModel.onClick.observe(this , Observer {
            when(it?.id){
                R.id.tvSignUp->{
                    val intent = Intent(this@SplashSecondActivity , SignUpActivity::class.java)
                    startActivity(intent)
                }
                R.id.tvLogin->{
                    val intent = Intent(this@SplashSecondActivity , LoginActivity::class.java)
                    startActivity(intent)
                }
            }
        })
    }
}