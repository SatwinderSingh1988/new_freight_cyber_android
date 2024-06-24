package com.app.freightCyber.presentation.auth.authentication_method

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.freightCyber.BR
import com.app.freightCyber.R
import com.app.freightCyber.domain.model.dummy_data.AuthenticationData
import com.app.freightCyber.databinding.ActivityAuthenticationCompleteBinding
import com.app.freightCyber.databinding.AuthenticationListItem2Binding
import com.app.freightCyber.core.base.activity.BaseActivity
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.recycler_view.SimpleRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthenticationCompleteActivity : BaseActivity<ActivityAuthenticationCompleteBinding>() {

    private val viewModel: AuthenticationActivityVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.activity_authentication_complete
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView() {
        setStatusTextColor()
        initOnClick()
        setUpAdapter()
    }


    private var authAdapter : SimpleRecyclerViewAdapter<AuthenticationData, AuthenticationListItem2Binding>? = null
    @SuppressLint("NotifyDataSetChanged")
    private fun setUpAdapter() {
        authAdapter =
            SimpleRecyclerViewAdapter(
                R.layout.authentication_list_item_2, BR.bean,
                SimpleRecyclerViewAdapter.SimpleCallback { v, m, pos ->
                    when (v.id) {
                        R.id.switchBtn -> {

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

    private fun initOnClick() {
        viewModel.onClick.observe(this, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    onBackPressedDispatcher.onBackPressed()
                }
                R.id.tvNext -> {
                    startActivity(Intent(this, ChooseAuthenticationActivity::class.java))
                }
            }
        })
    }

    private fun setStatusTextColor() {
        window.decorView.systemUiVisibility = 0
    }
}