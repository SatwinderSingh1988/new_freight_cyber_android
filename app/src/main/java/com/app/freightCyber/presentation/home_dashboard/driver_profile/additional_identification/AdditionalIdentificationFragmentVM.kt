package com.app.freightCyber.presentation.home_dashboard.driver_profile.additional_identification

import androidx.lifecycle.viewModelScope
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.domain.model.response.AdditionalIdentification
import com.app.freightCyber.domain.model.response.AdditionalIdentificationResponse
import com.app.freightCyber.domain.model.response.DriverProfileResponse
import com.app.freightCyber.domain.model.response.GetAdditionalIdentificationResponse
import com.app.freightCyber.domain.usecase.GetAdditionalIdentificationUseCase
import com.app.freightCyber.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdditionalIdentificationFragmentVM @Inject constructor(private val useCase : GetAdditionalIdentificationUseCase): BaseViewModel() {

    private val _additionalIdentificationResult = MutableStateFlow<NetworkResult<GetAdditionalIdentificationResponse>?>(null)
    val additionalIdentificationResult: StateFlow<NetworkResult<GetAdditionalIdentificationResponse>?> get() = _additionalIdentificationResult

    init {
        getAdditionalIdentification()
    }
    private fun getAdditionalIdentification() {
        viewModelScope.launch {
            useCase.invoke()
                .catch { e ->
                    _additionalIdentificationResult.value = NetworkResult.Error(e)
                }
                .collect { result ->
                    _additionalIdentificationResult.value = result
                }
        }
    }
}