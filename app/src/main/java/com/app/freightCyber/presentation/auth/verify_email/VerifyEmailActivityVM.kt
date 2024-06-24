package com.app.freightCyber.presentation.auth.verify_email

import androidx.lifecycle.viewModelScope
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.domain.model.request.GeneratePasscodeRequest
import com.app.freightCyber.domain.model.response.SimpleApiResponse
import com.app.freightCyber.domain.usecase.GeneratePasscodeUseCase
import com.app.freightCyber.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyEmailActivityVM @Inject constructor(private val generatePasscodeUseCase: GeneratePasscodeUseCase) :
    BaseViewModel() {

    private val _passcodeResult = MutableStateFlow<NetworkResult<SimpleApiResponse>?>(null)
    val passcodeResult: StateFlow<NetworkResult<SimpleApiResponse>?> get() = _passcodeResult

    fun generatePasscode(generatePasscode: GeneratePasscodeRequest) {
        viewModelScope.launch {
            generatePasscodeUseCase.invoke(generatePasscode)
                .catch { e ->
                    _passcodeResult.value = NetworkResult.Error(e)
                }
                .collect { result ->
                    _passcodeResult.value = result
                }
        }
    }

    fun resetUpdateGeneratePasscodeResult() {
        _passcodeResult.value = null
    }
}