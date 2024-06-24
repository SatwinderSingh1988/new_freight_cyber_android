package com.app.freightCyber.presentation.auth.additional_identification

import androidx.lifecycle.viewModelScope
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.domain.model.request.AdditionalIdentificationRequest
import com.app.freightCyber.domain.model.response.AdditionalIdentificationResponse
import com.app.freightCyber.domain.usecase.AdditionalIdentificationUseCase
import com.app.freightCyber.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AdditionalIdentificationActivityVM @Inject constructor(private val additionalIdentificationUseCase: AdditionalIdentificationUseCase) :
    BaseViewModel() {

    private val _addIdentificationResult: MutableStateFlow<NetworkResult<AdditionalIdentificationResponse>?> =
        MutableStateFlow(null)
    val addIdentificationResult: StateFlow<NetworkResult<AdditionalIdentificationResponse>?> get() = _addIdentificationResult
    fun addAdditionalIdentification(additionalIdentificationRequest: AdditionalIdentificationRequest) {
        viewModelScope.launch {
            additionalIdentificationUseCase.invoke(additionalIdentificationRequest)
                .catch { e ->
                    _addIdentificationResult.value = NetworkResult.Error(e)
                }.collect {
                    _addIdentificationResult.value = it
                }
        }
    }

    fun resetUpdateAdditionalResult() {
        _addIdentificationResult.value = null
    }
}