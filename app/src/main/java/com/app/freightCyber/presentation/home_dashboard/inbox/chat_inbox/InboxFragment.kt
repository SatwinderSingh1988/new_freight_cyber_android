package com.app.freightCyber.presentation.home_dashboard.inbox.chat_inbox

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.BR
import com.app.freightCyber.R
import com.app.freightCyber.domain.model.dummy_data.AuthenticationData
import com.app.freightCyber.databinding.FragmentInboxBinding
import com.app.freightCyber.databinding.InboxListItemBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.recycler_view.SimpleRecyclerViewAdapter
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InboxFragment : BaseFragment<FragmentInboxBinding>() {

    private val viewModel: InboxFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_inbox
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        setInboxAdapter()
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.container -> {

                }
            }
        })
    }

    private var inboxAdapter : SimpleRecyclerViewAdapter<AuthenticationData, InboxListItemBinding>? = null
    @SuppressLint("NotifyDataSetChanged")
    private fun setInboxAdapter() {
        inboxAdapter =
            SimpleRecyclerViewAdapter(R.layout.inbox_list_item,
                BR.bean,
                SimpleRecyclerViewAdapter.SimpleCallback { v, m, pos ->
                    when (v.id) {
                        R.id.csRoot, R.id.tvName, R.id.tvLastMessage -> {
                            HelperUtils.navigateWithSlideAnimations(
                                findNavController(),
                                R.id.chatFragment
                            )
                        }
                    }
                })
        binding.rvInbox.adapter = inboxAdapter
        inboxAdapter?.list = authList()
    }


    private fun authList() : ArrayList<AuthenticationData>{
        val list = ArrayList<AuthenticationData>()
        list.add(AuthenticationData(R.drawable.img_fingerprint ,"1. Enable Fingerprint","Tap to set up fingerprint" ,false))
        list.add(AuthenticationData(R.drawable.img_facial ,"2. Facial Recognition","Tap to set up face" , false))
        list.add(AuthenticationData(R.drawable.img_audio ,"3. Voice Registration","Tap to set up voice" , false))
        list.add(AuthenticationData(R.drawable.img_pass_code ,"4. Passcode","Tap to set up voice" , false))
        list.add(AuthenticationData(R.drawable.img_fingerprint ,"1. Enable Fingerprint","Tap to set up fingerprint" ,false))
        list.add(AuthenticationData(R.drawable.img_facial ,"2. Facial Recognition","Tap to set up face" , false))
        list.add(AuthenticationData(R.drawable.img_audio ,"3. Voice Registration","Tap to set up voice" , false))
        list.add(AuthenticationData(R.drawable.img_pass_code ,"4. Passcode","Tap to set up voice" , false))
        return list
    }
}