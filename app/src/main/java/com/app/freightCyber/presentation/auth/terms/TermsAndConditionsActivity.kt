package com.app.freightCyber.presentation.auth.terms

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.freightCyber.BR
import com.app.freightCyber.R
import com.app.freightCyber.domain.model.dummy_data.TermsData
import com.app.freightCyber.databinding.ActivityTermsBinding
import com.app.freightCyber.databinding.TermsItemListDesignBinding
import com.app.freightCyber.core.base.activity.BaseActivity
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.recycler_view.SimpleRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermsAndConditionsActivity : BaseActivity<ActivityTermsBinding>() {

    private val viewModel: TermsAndConditionsActivityVM by viewModels()


    override fun getLayoutResource(): Int {
        return R.layout.activity_terms
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
            }
        })
    }

    private fun setStatusTextColor() {
        window.decorView.systemUiVisibility = 0
    }

    private var termsAdapter: SimpleRecyclerViewAdapter<TermsData, TermsItemListDesignBinding>? =
        null

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpAdapter() {
        termsAdapter =
            SimpleRecyclerViewAdapter(R.layout.terms_item_list_design,
                BR.bean,
                SimpleRecyclerViewAdapter.SimpleCallback { v, m, pos ->
                    when (v.id) {
                        R.id.tvTermsText, R.id.imgRight -> {
                            when (m.value) {
                                "Freight Cyber Services Agreement" -> {
                                    val intent =
                                        Intent(this, FrieghtCyberServicesActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }

                                "Facial Recognition Safety Feature" -> {
                                    val intent =
                                        Intent(this, FacialSafetyFeatureActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }

                                "GPS Vehicle tracking" -> {
                                    val intent =
                                        Intent(this, GPSVehicleTrackingActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }

                                "Enable Location" -> {
                                    val intent = Intent(this, EnableLocationActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }

                                "Compliance with Laws" -> {
                                    val intent =
                                        Intent(this, ComplianceWithLawsActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }

                                "Data transfer to record keeper" -> {
                                    val intent =
                                        Intent(this, DataTransferToRecordKeeperActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            }
                        }
                    }
                })
        binding.rvTerms.adapter = termsAdapter
        termsAdapter?.list = termsList()
    }


    private fun termsList(): ArrayList<TermsData> {
        val list = ArrayList<TermsData>()
        list.add(TermsData("Freight Cyber Services Agreement"))
        list.add(TermsData("Facial Recognition Safety Feature"))
        list.add(TermsData("GPS Vehicle tracking"))
        list.add(TermsData("Enable Location"))
        list.add(TermsData("Compliance with Laws"))
        list.add(TermsData("Data transfer to record keeper"))
        return list
    }
}
