package com.app.freightCyber.presentation.auth.emergency_details

import androidx.lifecycle.viewModelScope
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.domain.model.request.EmergencyContactRequest
import com.app.freightCyber.domain.model.response.EmergencyContactResponse
import com.app.freightCyber.domain.usecase.EmergencyContactUseCase
import com.app.freightCyber.utils.NetworkResult
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EmergencyActivityVM @Inject constructor(private val emergencyContactUseCase: EmergencyContactUseCase): BaseViewModel() {

    private val _emergencyResult = MutableStateFlow<NetworkResult<EmergencyContactResponse>?>(null)
    val emergencyResult : StateFlow<NetworkResult<EmergencyContactResponse>?> get() = _emergencyResult
    fun emergencyContact(emergencyContactRequest: EmergencyContactRequest){
        viewModelScope.launch {
            emergencyContactUseCase.invoke(emergencyContactRequest)
                .catch {e ->
                    _emergencyResult.value = NetworkResult.Error(e)
                }
                .collect{
                    _emergencyResult.value = it
                }
        }
    }

    fun resetUpdateEmergencyResult() {
        _emergencyResult.value = null
    }
}

