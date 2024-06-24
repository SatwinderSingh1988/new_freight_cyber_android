package com.app.freightCyber.presentation.home_dashboard.driver_profile.additional_identification

import androidx.lifecycle.viewModelScope
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.domain.model.request.AdditionalIdentificationRequest
import com.app.freightCyber.domain.model.response.AdditionalIdentificationResponse
import com.app.freightCyber.domain.usecase.UpdateAdditionalIdentificationUseCase
import com.app.freightCyber.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAdditionalFragmentVM @Inject constructor(private var useCase: UpdateAdditionalIdentificationUseCase): BaseViewModel() {

    private val _additionalIdentificationResult = MutableStateFlow<NetworkResult<AdditionalIdentificationResponse>?>(null)
    val additionalIdentificationResult: StateFlow<NetworkResult<AdditionalIdentificationResponse>?> get() = _additionalIdentificationResult

    fun updateAdditionalIdentification(additionalIdentificationRequest: AdditionalIdentificationRequest) {
        viewModelScope.launch {
            useCase.invoke(additionalIdentificationRequest)
                .catch { e ->
                    _additionalIdentificationResult.value = NetworkResult.Error(e)
                }
                .collect { result ->
                    _additionalIdentificationResult.value = result
                }
        }
    }
}