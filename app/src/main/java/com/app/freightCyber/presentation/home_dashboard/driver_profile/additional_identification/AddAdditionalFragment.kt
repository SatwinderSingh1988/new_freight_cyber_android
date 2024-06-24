package com.app.freightCyber.presentation.home_dashboard.driver_profile.additional_identification


import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentAddAdditionalBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.domain.model.request.AdditionalIdentificationRequest
import com.app.freightCyber.utils.HelperUtils
import com.app.freightCyber.utils.NetworkResult
import com.app.freightCyber.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AddAdditionalFragment : BaseFragment<FragmentAddAdditionalBinding>() {

    private val viewModel: AddAdditionalFragmentVM by viewModels()
    private var countryOfIssueList: List<String>? = null
    private var idTypeList: List<String>? = null


    override fun getLayoutResource(): Int {
        return R.layout.fragment_add_additional
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        idTypeList = listOf("Item1", "Item2", "Item3")
        countryOfIssueList = listOf("India", "United State", "Canada")
        addTextWatcher(binding.etIssue)
        addTextWatcher(binding.etExpiry)
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.additionalIdentificationResult.collect { result ->
                    when (result) {
                        is NetworkResult.Error -> {
                            hideNewLoading()
                            result.exception.message?.let { showToast(it) }
                        }

                        is NetworkResult.Loading -> {
                            showNewLoading()
                        }

                        is NetworkResult.Success -> {
                            hideNewLoading()
                           showToast(result.data.message.toString())
                        }
                        null -> {}
                    }
                }
            }
        }
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    findNavController().popBackStack()
                }
                R.id.tvNext -> {
                    checkValidation()
                }
                R.id.etCountryIssue -> {
                    if (countryOfIssueList != null) {
                        HelperUtils.showPopupMenu(requireContext(), binding.etCountryIssue, countryOfIssueList!!)
                    }
                }

                R.id.etAdditionalId -> {
                    if (idTypeList != null) {
                        HelperUtils.showPopupMenu(requireContext(), binding.etAdditionalId, idTypeList!!)
                    }
                }
            }
        })
    }

    private fun checkValidation() {
        if (TextUtils.isEmpty(binding.etAdditionalId.text.toString())) {
            showToast(getString(R.string.please_fill_all_the_details))
            return
        }
        if (TextUtils.isEmpty(binding.etPasssportNumber.text.toString())) {
            showToast(getString(R.string.please_fill_all_the_details))
            return
        }
        if (TextUtils.isEmpty(binding.etCountryIssue.text.toString())) {
            showToast(getString(R.string.please_fill_all_the_details))
            return
        }
        if (TextUtils.isEmpty(binding.etIssue.text.toString())) {
            showToast(getString(R.string.please_fill_all_the_details))
            return
        }
        if (TextUtils.isEmpty(binding.etExpiry.text.toString())) {
            showToast(getString(R.string.please_fill_all_the_details))
            return
        }


        viewModel.updateAdditionalIdentification(
            AdditionalIdentificationRequest(
                expiry = binding.etExpiry.text.toString(),
                id_type =  binding.etAdditionalId.text.toString() ,
                issue = binding.etIssue.text.toString() ,
                passport_number = binding.etPasssportNumber.text.toString() ,
                country_of_issue = binding.etCountryIssue.text.toString()
            )
        )


    }

    private fun addTextWatcher(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            var currentLength = 0
            var isDeleting = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                currentLength = s?.length ?: 0
                isDeleting = count > after
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val input = s.toString()
                if (input.length == 2 && currentLength < input.length && !isDeleting) {
                    editText.setText("$input/")
                    editText.setSelection(input.length + 1)
                }
            }
        })
    }

}