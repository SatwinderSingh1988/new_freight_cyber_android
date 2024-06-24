package com.app.freightCyber.presentation.home_dashboard.notification

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.BR
import com.app.freightCyber.R
import com.app.freightCyber.domain.model.dummy_data.AuthenticationData
import com.app.freightCyber.databinding.FragmentNotificationBinding
import com.app.freightCyber.databinding.NotificationItemListBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.recycler_view.SimpleRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NotificationFragment : BaseFragment<FragmentNotificationBinding>() {

    private val viewModel: NotificationFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_notification
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        notificationAdapter()
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    findNavController().popBackStack()
                }

            }
        })
    }

    private var authAdapter : SimpleRecyclerViewAdapter<AuthenticationData, NotificationItemListBinding>? = null
    @SuppressLint("NotifyDataSetChanged")
    private fun notificationAdapter() {
        authAdapter =
            SimpleRecyclerViewAdapter(R.layout.notification_item_list,
                BR.bean,
                SimpleRecyclerViewAdapter.SimpleCallback { v, m, pos ->
                    when (v.id) {

                    }
                })
        binding.rvNotification.adapter = authAdapter
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
}