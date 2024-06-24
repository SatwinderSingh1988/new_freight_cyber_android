package com.app.freightCyber.presentation.home_dashboard.driver_profile.emergency_contact

import androidx.lifecycle.viewModelScope
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.domain.model.request.EmergencyContactRequest
import com.app.freightCyber.domain.model.response.EmergencyContactResponse
import com.app.freightCyber.domain.usecase.UpdateEmergencyContactUseCase
import com.app.freightCyber.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmergencyEditFragmentVM @Inject constructor(private val updateEmergencyContactUseCase: UpdateEmergencyContactUseCase) :
    BaseViewModel() {
    private val _emergencyResult = MutableStateFlow<NetworkResult<EmergencyContactResponse>?>(null)
    val emergencyResult: StateFlow<NetworkResult<EmergencyContactResponse>?> get() = _emergencyResult


    fun updateEmergencyContactResult(emergencyContactRequest: EmergencyContactRequest) {
        viewModelScope.launch {
            updateEmergencyContactUseCase.invoke(emergencyContactRequest)
                .catch { e ->
                    _emergencyResult.value = NetworkResult.Error(e)
                }
                .collectLatest { result ->
                    _emergencyResult.value = result
                }
        }
    }
}