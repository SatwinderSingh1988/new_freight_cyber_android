package com.app.freightCyber.presentation.auth.create_Profile

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.freightCyber.BR
import com.app.freightCyber.R
import com.app.freightCyber.domain.model.dummy_data.CreateProfileData
import com.app.freightCyber.databinding.ActivityCreateProfileBinding
import com.app.freightCyber.databinding.CreateProfileListItemBinding
import com.app.freightCyber.presentation.auth.personal_details.PersonalDetailActivity
import com.app.freightCyber.presentation.auth.scan_driver_license.ScanDriverLicenseActivity
import com.app.freightCyber.core.base.activity.BaseActivity
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.recycler_view.SimpleRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateProfileActivity : BaseActivity<ActivityCreateProfileBinding>() {

    private val viewModel: CreateProfileActivityVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.activity_create_profile
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView() {
        setStatusTextColor()
        initOnClick()
        setUpAdapter()
    }

    private fun initOnClick() {
        viewModel.onClick.observe(this, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    onBackPressedDispatcher.onBackPressed()
                }
                R.id.tvNext -> {
                    val intent = Intent(this , PersonalDetailActivity::class.java)
                    startActivity(intent)
                }
            }
        })
    }

    private fun setStatusTextColor() {
        window.decorView.systemUiVisibility = 0
    }

    private var profileAdapter: SimpleRecyclerViewAdapter<CreateProfileData, CreateProfileListItemBinding>? =
        null

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpAdapter() {
        profileAdapter =
            SimpleRecyclerViewAdapter(R.layout.create_profile_list_item,
                BR.bean,
                SimpleRecyclerViewAdapter.SimpleCallback { v, m, pos ->
                    when (m.image) {
                        R.drawable.img_driver_licence -> {
                            val intent = Intent(this, ScanDriverLicenseActivity::class.java)
                            startActivity(intent)
                        }

                        R.drawable.img_manual -> {
                            val intent = Intent(this, PersonalDetailActivity::class.java)
                            startActivity(intent)
                        }
                    }
                })
        binding.rvProfileData.adapter = profileAdapter
        profileAdapter?.list = profileListData()
    }

    private fun profileListData(): ArrayList<CreateProfileData> {
        val list = ArrayList<CreateProfileData>()
        list.add(CreateProfileData(R.drawable.img_driver_licence, "By scanning driver’s license"))
        list.add(CreateProfileData(R.drawable.img_manual, "Manual Entry"))
        return list
    }
}