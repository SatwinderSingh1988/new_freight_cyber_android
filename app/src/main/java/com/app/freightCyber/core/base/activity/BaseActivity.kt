package com.app.freightCyber.core.base.activity

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.app.freightCyber.app.App
import com.app.freightCyber.BR
import com.app.freightCyber.R
import com.app.freightCyber.data.source.local.prefrences.SharedPrefManager
import com.app.freightCyber.core.network.ErrorCodes
import com.app.freightCyber.core.network.NetworkError
import com.app.freightCyber.databinding.ViewProgressSheetBinding
import com.app.freightCyber.core.base.connectivity.ConnectivityProvider
import com.app.freightCyber.utils.AlertManager
import com.app.freightCyber.utils.event.NoInternetSheet
import com.app.freightCyber.utils.hideKeyboard
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.core.base.progress_dialog.ProgressDialogAvl
import com.app.freightCyber.core.base.progress_sheet.ProgressSheet
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

abstract class BaseActivity<Binding : ViewDataBinding> : AppCompatActivity(),
    ConnectivityProvider.ConnectivityStateListener {
    lateinit var progressDialogAvl: ProgressDialogAvl
    private  var progressSheet: ProgressSheet?=null
    open val onRetry: (() -> Unit)? = null


    var onStartCount = 0
    lateinit var binding: Binding
    val app: App get() = application as App

    private lateinit var connectivityProvider: ConnectivityProvider
    private var noInternetSheet: NoInternetSheet? = null

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onStartCount = 1
        if (savedInstanceState == null) // 1st time
        {
            this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
        } else // already created so reverse animation
        {
            onStartCount = 2
        }
        val layout: Int = getLayoutResource()
        binding = DataBindingUtil.setContentView(this, layout)
        binding.setVariable(BR.vm, getViewModel())
        connectivityProvider = ConnectivityProvider.createProvider(this)
        connectivityProvider.addListener(this)
        progressDialogAvl =
            ProgressDialogAvl(
                this
            )

        onCreateView()
    }

    @Inject
    lateinit var sharedPrefManager: SharedPrefManager

    protected abstract fun getLayoutResource(): Int
    protected abstract fun getViewModel(): BaseViewModel
    protected abstract fun onCreateView()

    fun showToast(msg: String? = "Something went wrong !!") {
        Toast.makeText(this, msg ?: "Showed null value !!", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        hideKeyboard()
    }

    fun showLoading(s: String?) {
        progressSheet?.dismissAllowingStateLoss()
        progressSheet = ProgressSheet(object : ProgressSheet.BaseCallback {
            override fun onClick(view: View?) {}
            override fun onBind(bind: ViewProgressSheetBinding) {
                progressSheet?.showMessage(s);
            }
        })
        progressSheet?.isCancelable=false
        progressSheet?.show(supportFragmentManager, progressSheet?.tag)

    }

    fun hideLoading() {
        progressSheet?.dismissAllowingStateLoss()
    }


    fun hideNewLoading() {
        progressDialogAvl.isLoading(false)
        //  progressSheet?.dismissAllowingStateLoss()
    }

    fun showNewLoading() {
        progressDialogAvl.isLoading(true)
    }



    fun onError(error: Throwable, showErrorView: Boolean) {
        if (error is NetworkError) {

            when (error.errorCode) {
                ErrorCodes.SESSION_EXPIRED -> {
                    showToast(getString(R.string.session_expired))
                    app.onLogout()
                }

                else -> AlertManager.showNegativeAlert(
                    this,
                    error.message,
                    getString(R.string.alert)
                )
            }
        } else {
            AlertManager.showNegativeAlert(
                this,
                getString(R.string.please_try_again),
                getString(R.string.alert)
            )
        }
    }

    override fun onDestroy() {

        connectivityProvider.removeListener(this)
        super.onDestroy()
    }


    override fun onStateChange(state: ConnectivityProvider.NetworkState) {
        if (noInternetSheet == null) {
            noInternetSheet = NoInternetSheet()
            noInternetSheet?.isCancelable = false
        }
        if (state.hasInternet()) {
            if (noInternetSheet?.isVisible == true)
                noInternetSheet?.dismiss()
        } else {
            if (noInternetSheet?.isVisible == false)
                noInternetSheet?.show(supportFragmentManager, noInternetSheet?.tag)
        }
    }

    private fun ConnectivityProvider.NetworkState.hasInternet(): Boolean {
        return (this as? ConnectivityProvider.NetworkState.ConnectedState)?.hasInternet == true
    }

    override fun onStart() {
        super.onStart()
        if (onStartCount > 1) {
            this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
        } else if (onStartCount == 1) {
            onStartCount++
        }
    }
    fun textToRequestBody(text: String?): RequestBody {
        return text!!.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    fun multipartImageBody(image: File): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            "profile",
//            "profileImage",
            image.name,
            RequestBody.create("image/png".toMediaTypeOrNull(), image)
        )
    }

    fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm = getSystemService(InputMethodManager::class.java)
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    fun setStatusBarColor(){
    this.window.statusBarColor  = ContextCompat.getColor(this, R.color.black)
    }

}