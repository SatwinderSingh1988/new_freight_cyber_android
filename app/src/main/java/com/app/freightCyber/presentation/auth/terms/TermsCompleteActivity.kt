package com.app.freightCyber.presentation.auth.terms

import android.annotation.SuppressLint
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.freightCyber.BR
import com.app.freightCyber.R
import com.app.freightCyber.utils.Constants
import com.app.freightCyber.domain.model.dummy_data.TermsData
import com.app.freightCyber.databinding.ActivityTermsCompleteBinding
import com.app.freightCyber.databinding.TermsCompleteItemListDesignBinding
import com.app.freightCyber.core.base.activity.BaseActivity
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.recycler_view.SimpleRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermsCompleteActivity : BaseActivity<ActivityTermsCompleteBinding>() {

    private val viewModel: TermsAndConditionsActivityVM by viewModels()


    override fun getLayoutResource(): Int {
        return R.layout.activity_terms_complete
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
                    Constants.termsLiveData.postValue(true)
                    showToast("Terms and conditions accepted.")
                    onBackPressedDispatcher.onBackPressed()
                }
                R.id.tvNext -> {
                    Constants.termsLiveData.postValue(true)
                    showToast("Terms and conditions accepted.")
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }
    private fun setStatusTextColor() {
        window.decorView.systemUiVisibility = 0
    }

    private var termsAdapter : SimpleRecyclerViewAdapter<TermsData, TermsCompleteItemListDesignBinding>? = null
    @SuppressLint("NotifyDataSetChanged")
    private fun setUpAdapter() {
        termsAdapter =
            SimpleRecyclerViewAdapter(R.layout.terms_complete_item_list_design,
                BR.bean,
                SimpleRecyclerViewAdapter.SimpleCallback { v, m, pos ->
                    when (v.id) {

                    }
                })
        binding.rvTerms.adapter = termsAdapter
        termsAdapter?.list = termsList()
    }

    private fun termsList() : ArrayList<TermsData>{
        val list = ArrayList<TermsData>()
        list.add(TermsData("Freight Cyber Services Agreement"))
        list.add(TermsData("Facial Recognition Safety Feature"))
        list.add(TermsData("GPS Vehicle tracking"))
        list.add(TermsData("Enable Location"))
        list.add(TermsData("Compliance with Laws"))
        list.add(TermsData("Data transfer to record keeper"))
        return list
    }

    override fun onBackPressed() {
        Constants.termsLiveData.postValue(true)
        super.onBackPressedDispatcher.onBackPressed()
    }
}
