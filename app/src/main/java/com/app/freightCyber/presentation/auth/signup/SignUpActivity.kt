package com.app.freightCyber.presentation.auth.signup

import android.content.Intent
import android.content.res.ColorStateList
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.app.freightCyber.R
import com.app.freightCyber.utils.Constants
import com.app.freightCyber.databinding.ActivitySignUpBinding
import com.app.freightCyber.presentation.auth.terms.TermsAndConditionsActivity
import com.app.freightCyber.presentation.auth.verify_otp.VerifyOtpActivity
import com.app.freightCyber.core.base.activity.BaseActivity
import com.app.freightCyber.core.base.view_model.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpActivity : BaseActivity<ActivitySignUpBinding>() {

    private val viewModel: SignUpActivityVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.activity_sign_up
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView() {
        setStatusTextColor()
        initOnClick()
        isTermsAndPolicyAccepted()
        checkTermsAcceptedOrNot()
        setStatusBarColor()
        // binding.countryCode.setCountryForPhoneCode(it.toInt())
    }

    private fun checkTermsAcceptedOrNot() {
        Constants.termsLiveData.observe(this , Observer {
            binding.cbTerms.visibility = View.VISIBLE
            binding.cbTerms.isChecked = true
        })
    }

    private fun isTermsAndPolicyAccepted() {
        binding.cbTerms.setOnCheckedChangeListener { buttonView, isChecked ->
           if(isChecked){
               binding.tvVerifyAccount.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this ,R.color.text_color_orange))
               binding.tvVerifyAccount.setTextColor(ContextCompat.getColor(this , R.color.black))
           }else{
               binding.tvVerifyAccount.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this ,R.color.black_2))
               binding.tvVerifyAccount.setTextColor(ContextCompat.getColor(this , R.color.grey2))
           }
        }
    }

    private fun initOnClick() {
        viewModel.onClick.observe(this , Observer {
            when(it?.id){

                R.id.imgBack->{
                   onBackPressedDispatcher.onBackPressed()
                }

                R.id.tvByCont->{
                    val intent = Intent(this, TermsAndConditionsActivity::class.java)
                    startActivity(intent)
                }

                R.id.tvVerifyAccount->{
                    if( binding.cbTerms.isChecked){
                        val intent = Intent(this@SignUpActivity, VerifyOtpActivity::class.java)
                        intent.putExtra("FROM" , "SIGNUP_SCREEN")
                        startActivity(intent)
                    }else{
                        showToast("please accept the Application's terms and conditions")
                    }
                }
            }
        })
    }

    private fun setStatusTextColor() {
        window.decorView.systemUiVisibility = 0
    }


}
