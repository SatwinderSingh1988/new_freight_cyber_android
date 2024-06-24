package com.app.freightCyber.presentation.home_dashboard.settings

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.BR
import com.app.freightCyber.R
import com.app.freightCyber.domain.model.dummy_data.SettingsData
import com.app.freightCyber.databinding.DeleteAccountDialogBinding
import com.app.freightCyber.databinding.FragmentSettingsBinding
import com.app.freightCyber.databinding.SettingsItemListBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.recycler_view.SimpleRecyclerViewAdapter
import com.app.freightCyber.core.base.dialog.BaseCustomDialog
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    private val viewModel: SettingsFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_settings
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        settingAdapter()
        showDeleteAccount()
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    findNavController().popBackStack()
                }
                R.id.tvDeleteAccount -> {
                    deleteAccountDialog?.show()
                }
            }
        })
    }

    private var deleteAccountDialog: BaseCustomDialog<DeleteAccountDialogBinding>? = null
    private fun showDeleteAccount() {
            deleteAccountDialog =
                BaseCustomDialog(
                    requireContext(),
                    R.layout.delete_account_dialog,
                    BaseCustomDialog.Listener {
                        when (it.id) {
                            R.id.tvYes -> {
                                deleteAccountDialog?.dismiss()
                            }

                            R.id.tvNo -> {
                                deleteAccountDialog?.dismiss()
                            }
                        }
                    })
            deleteAccountDialog?.setCancelable(false)
    }


    private var authAdapter: SimpleRecyclerViewAdapter<SettingsData, SettingsItemListBinding>? =
        null

    @SuppressLint("NotifyDataSetChanged")
    private fun settingAdapter() {
        authAdapter =
            SimpleRecyclerViewAdapter(R.layout.settings_item_list,
                BR.bean,
                SimpleRecyclerViewAdapter.SimpleCallback { v, m, pos ->
                    when (m.text) {
                        "Profile Settings" -> {
                            HelperUtils.navigateWithSlideAnimations(
                                findNavController(),
                                R.id.profileSettingsFragment
                            )
                        }
                    }
                })
        binding.rvSettings.adapter = authAdapter
        authAdapter?.list = authList()
    }


    private fun authList(): ArrayList<SettingsData> {
        val list = ArrayList<SettingsData>()
        list.add(SettingsData(R.drawable.profile_setting, "Profile Settings"))
        list.add(SettingsData(R.drawable.terms_settings, "Terms & Conditions"))
        return list
    }
}