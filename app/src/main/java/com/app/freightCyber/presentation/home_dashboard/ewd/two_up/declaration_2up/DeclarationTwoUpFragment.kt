package com.app.freightCyber.presentation.home_dashboard.ewd.two_up.declaration_2up

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.freightCyber.BR
import com.app.freightCyber.R
import com.app.freightCyber.domain.model.dummy_data.DeclarationList
import com.app.freightCyber.databinding.DeclarationListItemBinding
import com.app.freightCyber.databinding.FragmentDeclarationTwoUpBinding
import com.app.freightCyber.core.base.fragment.BaseFragment
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.recycler_view.SimpleRecyclerViewAdapter
import com.app.freightCyber.utils.HelperUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DeclarationTwoUpFragment : BaseFragment<FragmentDeclarationTwoUpBinding>() {

    private val viewModel: DeclarationTwoUpFragmentVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_declaration_two_up
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
                R.id.tvNext ->{
                    val bundle = Bundle()
                    bundle.putString("FROM" , "FROM_2UP")
                     HelperUtils.navigateWithSlideAnimations(findNavController() , R.id.addConfirmDetailFragment , bundle)
                }
            }
        })
    }

    private var innerAdapter: SimpleRecyclerViewAdapter<DeclarationList, DeclarationListItemBinding>? = null
    private fun setUpAdapter() {
        innerAdapter =
            SimpleRecyclerViewAdapter(R.layout.declaration_list_item,
                BR.bean,
                SimpleRecyclerViewAdapter.SimpleCallback { v, m, pos ->
                    when (v.id) {
                        R.id.root, R.id.tv1, R.id.tv2, R.id.tvOld, R.id.imgRight -> {

                        }
                    }
                })
        binding.rvDeclaration.adapter = innerAdapter
        innerAdapter?.list = authList2()
    }

    private fun authList2(): ArrayList<DeclarationList> {
        val list = ArrayList<DeclarationList>()
        list.add(DeclarationList("I (driver name) confirm that I am engaged in a two-up driver arrangement. By submitting this confirmation you are declaring that: • The two-up driver identification details are not false or misleading. If the identification details are not correct, correct these details prior to submitting this confirmation. A statement that is false or misleading may be used in legal proceedings for an offence against the Heavy Vehicle National Law, or another law of a State or the Commonwealth of Australia. Making a false or misleading entry in an electronic work diary is an offence punishable by a fine of over \$10,000. "))
        list.add(DeclarationList("I have read and agree to the two up driving declaration."))
        return list
    }
}