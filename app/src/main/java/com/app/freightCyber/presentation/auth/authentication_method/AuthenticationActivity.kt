package com.app.freightCyber.presentation.auth.authentication_method

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.app.freightCyber.R
import com.app.freightCyber.BR
import com.app.freightCyber.domain.model.dummy_data.AuthenticationData
import com.app.freightCyber.databinding.ActivityAuthenticationBinding
import com.app.freightCyber.databinding.AuthenticationListItemBinding
import com.app.freightCyber.presentation.auth.authentication_method.finger.FingerPrintActivity
import com.app.freightCyber.core.base.activity.BaseActivity
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.recycler_view.SimpleRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationActivity : BaseActivity<ActivityAuthenticationBinding>() {

    private val viewModel: AuthenticationActivityVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.activity_authentication
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView() {
        setStatusTextColor()
        initOnClick()
        setUpAdapter()
    }


    private var authAdapter : SimpleRecyclerViewAdapter<AuthenticationData, AuthenticationListItemBinding>? = null
    @SuppressLint("NotifyDataSetChanged")
    private fun setUpAdapter() {
        authAdapter =
            SimpleRecyclerViewAdapter(R.layout.authentication_list_item,
                BR.bean,
                SimpleRecyclerViewAdapter.SimpleCallback { v, m, pos ->
                    when (v.id) {
                        R.id.switchBtn, R.id.csRoot -> {
                            /*  m.isSelect = authAdapter?.list?.get(pos)?.isSelect != true
                              authAdapter?.notifyDataSetChanged()
                              checkSwitchButtonValues()*/
                        }
                    }
                })
        binding.rvAuthenticationMethods.adapter = authAdapter
        authAdapter?.list = authList()
    }


    private fun authList() : ArrayList<AuthenticationData>{
        val list = ArrayList<AuthenticationData>()
        list.add(AuthenticationData(R.drawable.img_fingerprint ,"1. Enable Fingerprint","Tap to set up fingerprint" ,false))
        list.add(AuthenticationData(R.drawable.img_facial ,"2. Facial Recognition","Tap to set up face" , false))
        list.add(AuthenticationData(R.drawable.img_audio ,"3. Voice Registration","Tap to set up voice" , false))
        list.add(AuthenticationData(R.drawable.img_pass_code ,"4. Passcode","Tap to set up voice" , false))
        return list
    }

    private fun checkSwitchButtonValues() {
        if(authAdapter?.list?.size!! > 0){
            val count = authAdapter?.list?.count { listData -> listData.isSelect } ?: 0
            if (count >= 1) {
                binding.tvNext.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.text_color_orange))
                binding.tvNext.setTextColor(ContextCompat.getColor(this , R.color.black))
            } else {
                binding.tvNext.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.disable_btn_color))
                binding.tvNext.setTextColor(ContextCompat.getColor(this , R.color.view_line_color))
            }
        }
    }


    private fun initOnClick() {
        viewModel.onClick.observe(this, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    onBackPressedDispatcher.onBackPressed()
                }
                R.id.tvNext -> {
                    startActivity(Intent(this@AuthenticationActivity, FingerPrintActivity::class.java))
                    /*val count = authAdapter?.list?.count { listData -> listData.isSelect } ?: 0
                    if (count >= 1) {
                        startActivity(Intent(this@AuthenticationActivity, FingerPrintActivity::class.java))
                    } else {
                        binding.tvNext.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.disable_btn_color))
                        binding.tvNext.setTextColor(ContextCompat.getColor(this , R.color.view_line_color))
                        showToast("Please select an option.")
                    }*/
                }
            }
        })
    }

    private fun setStatusTextColor() {
        window.decorView.systemUiVisibility = 0
    }
}