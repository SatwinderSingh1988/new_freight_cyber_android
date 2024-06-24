package com.app.freightCyber.presentation.auth.splash

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import androidx.activity.viewModels
import com.app.freightCyber.R
import com.app.freightCyber.databinding.ActivitySplashFirstBinding
import com.app.freightCyber.core.base.activity.BaseActivity
import com.app.freightCyber.core.base.view_model.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFirstActivity : BaseActivity<ActivitySplashFirstBinding>() {

    private val viewModel : SplashFirstActivityVM by viewModels()

    override fun getLayoutResource(): Int {
      return R.layout.activity_splash_first
    }

    override fun getViewModel(): BaseViewModel {
     return viewModel
    }

    override fun onCreateView() {
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setStatusTextColor()
        Log.d("onCreateView: " , sharedPrefManager.getCurrentUser().toString())
        initHandler()
    }

    private fun initHandler() {
      Handler(Looper.getMainLooper()).postDelayed(Runnable {
          val intent = Intent(this@SplashFirstActivity , SplashSecondActivity::class.java)
          startActivity(intent)
          finish()
          /*if(sharedPrefManager.getCurrentUser() != null){
              val intent = Intent(this@SplashFirstActivity , HomeActivity::class.java)
              startActivity(intent)
              finish()
          }else{
              val intent = Intent(this@SplashFirstActivity , SplashSecondActivity::class.java)
              startActivity(intent)
              finish()
          }*/
      },2000)
    }

    private fun setStatusTextColor() {
        window.decorView.systemUiVisibility = 0
    }
}