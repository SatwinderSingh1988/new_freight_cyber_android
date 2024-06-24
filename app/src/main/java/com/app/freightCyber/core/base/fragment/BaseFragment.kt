package com.app.freightCyber.core.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.app.freightCyber.BR
import com.app.freightCyber.core.base.activity.BaseActivity
import com.app.freightCyber.core.base.progress_dialog.ProgressDialogAvl
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.data.source.local.prefrences.SharedPrefManager
import com.app.freightCyber.utils.hideKeyboard
import javax.inject.Inject


abstract class BaseFragment<Binding : ViewDataBinding> : Fragment() {
    lateinit var binding: Binding
    lateinit var progressDialogAvl: ProgressDialogAvl

    @Inject
    lateinit var sharedPrefManager: SharedPrefManager
    val parentActivity: BaseActivity<*>? get() = activity as? BaseActivity<*>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreateView(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout: Int = getLayoutResource()
        binding = DataBindingUtil.inflate(layoutInflater, layout, container, false)
        binding.setVariable(BR.vm, getViewModel())
        progressDialogAvl = ProgressDialogAvl(requireContext())
        return binding.root
    }

    fun hideNewLoading() {
        progressDialogAvl.isLoading(false)
    }

    fun showNewLoading() {
        progressDialogAvl.isLoading(true)
    }

    protected abstract fun getLayoutResource(): Int
    protected abstract fun getViewModel(): BaseViewModel
    protected abstract fun onCreateView(view: View)
    override fun onPause() {
        super.onPause()
        activity?.hideKeyboard()
    }

    fun showLoading(s: String?) {
        parentActivity?.showLoading(s)
    }

    fun showLoading(s: Int) {
        parentActivity?.showLoading(getString(s))
    }

}