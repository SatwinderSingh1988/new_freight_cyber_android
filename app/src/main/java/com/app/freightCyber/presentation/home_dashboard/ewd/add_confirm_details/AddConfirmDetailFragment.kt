package com.app.freightCyber.presentation.home_dashboard.ewd.add_confirm_details

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.R
import com.app.freightCyber.databinding.FragmentAddConfirmDetailBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddConfirmDetailFragment : BaseFragment<FragmentAddConfirmDetailBinding>() {

    private var data: String? = null
    private val viewModel: AddConfirmDetailFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_add_confirm_detail
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
         data = arguments?.getString("FROM")
         when (data) {
            "FROM_SOLO" -> {
                binding.tvHeader.text = "Vehicle details"
            }

            "FROM_2UP" -> {
                binding.tvHeader.text = "Please add/confirm details"
            }

            else -> {
                binding.tvHeader.text = "Please add/confirm details"
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
                    when (data) {
                        "FROM_SOLO" -> {
                            HelperUtils.navigateWithSlideAnimations(findNavController(), R.id.confirmDetailTwoFragment)
                        }
                        "FROM_2UP" -> {
                            HelperUtils.navigateWithSlideAnimations(findNavController(), R.id.twoUpConfirmDetailTwoFragment)
                        }
                    }

                }
            }
        })
    }
}