package com.app.freightCyber.presentation.home_dashboard.ewd.work_diary

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.BR
import com.app.freightCyber.R
import com.app.freightCyber.domain.model.dummy_data.AddDiarySwitch
import com.app.freightCyber.databinding.AddDiarySwitchItemBinding
import com.app.freightCyber.databinding.FragmentAddDairySwitchBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.recycler_view.SimpleRecyclerViewAdapter
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddDiarySwitchFragment : BaseFragment<FragmentAddDairySwitchBinding>() {

    private val viewModel: AddDiarySwitchFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_add_dairy_switch
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        initOnClick()
        setUpAdapter()
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

    private var innerAdapter: SimpleRecyclerViewAdapter<AddDiarySwitch, AddDiarySwitchItemBinding>? = null
    private fun setUpAdapter() {
        innerAdapter =
            SimpleRecyclerViewAdapter(R.layout.add_diary_switch_item,
                BR.bean,
                SimpleRecyclerViewAdapter.SimpleCallback { v, m, pos ->
                    when (pos) {
                        0, 1 -> {
                            HelperUtils.navigateWithSlideAnimations(
                                findNavController(),
                                R.id.WWDFragment
                            )
                        }
                    }
                })
        binding.rvAddDiary.adapter = innerAdapter
        innerAdapter?.list = authList2()
    }

    private fun authList2(): ArrayList<AddDiarySwitch> {
        val list = ArrayList<AddDiarySwitch>()
        list.add(AddDiarySwitch("I have switched from the WWD to the EWD" , R.drawable.add_diary_1))
        list.add(AddDiarySwitch("I have switched from the EWD to the WWD" ,R.drawable.add_diary_2))
        return list
    }

}