package com.app.freightCyber.core.base.view_model


import android.view.View
import androidx.lifecycle.ViewModel
import com.app.freightCyber.utils.event.SingleActionEvent

abstract class BaseViewModel : ViewModel() {

    val onClick: SingleActionEvent<View?> = SingleActionEvent()

    override fun onCleared() {
        super.onCleared()
    }

    open fun onClick(view: View?) {
        onClick.value = view
    }

}