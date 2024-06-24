package com.app.freightCyber.presentation.home_dashboard.job.report.report_damage

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.BR
import com.app.freightCyber.R
import com.app.freightCyber.domain.model.dummy_data.AuthenticationData
import com.app.freightCyber.databinding.FragmentReportDamageBinding
import com.app.freightCyber.databinding.GalleryImagesItemListBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.recycler_view.SimpleRecyclerViewAdapter
import com.app.freightCyber.utils.HelperUtils
import com.app.freightCyber.utils.showToast
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ReportDamageFragment : BaseFragment<FragmentReportDamageBinding>() {

    private val viewModel: ReportDamageFragmentVM by viewModels()

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                val fileUri = data?.data!!
                //   binding.imgProfile.setImageURI(fileUri)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                showToast(ImagePicker.getError(data))
            } else {
                showToast("Task Cancelled")
            }
        }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_report_damage
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        setUpImageAdapter()
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    findNavController().popBackStack()
                }
                R.id.etDamageLocation -> {
                   HelperUtils.showPopupMenu(requireContext() , binding.etDamageLocation , listOf("item1" , "item2" , "item3"))
                }
                R.id.tvSubmit -> {
                    HelperUtils.navigateWithSlideAnimations(findNavController(), R.id.reportSuccessFragment)
                }
                R.id.viewCertificate  , R.id.imgPlus -> {
                    ImagePicker.with(this)
                        .galleryOnly()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .createIntent { intent ->
                            startForProfileImageResult.launch(intent)
                        }
                }
            }
        })
    }

    private var imageAdapter : SimpleRecyclerViewAdapter<AuthenticationData, GalleryImagesItemListBinding>? = null
    @SuppressLint("NotifyDataSetChanged")
    private fun setUpImageAdapter() {
        imageAdapter =
            SimpleRecyclerViewAdapter(R.layout.gallery_images_item_list,
                BR.bean,
                SimpleRecyclerViewAdapter.SimpleCallback { v, m, pos ->
                    when (v.id) {
                        R.id.switchBtn -> {

                        }
                    }
                })
        binding.rvImages.adapter = imageAdapter
        imageAdapter?.list = authList2()
    }


    private fun authList2() : ArrayList<AuthenticationData>{
        val list = ArrayList<AuthenticationData>()
        list.add(AuthenticationData(R.drawable.dummy_gallery_img ,"1. Enable Fingerprint","Tap to set up fingerprint" ,false))
        list.add(AuthenticationData(R.drawable.dummy_gallery_img ,"1. Enable Fingerprint","Tap to set up fingerprint" ,false))
        list.add(AuthenticationData(R.drawable.dummy_gallery_img ,"1. Enable Fingerprint","Tap to set up fingerprint" ,false))
        return list
    }
}