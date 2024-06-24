package com.app.freightCyber.presentation.auth.license

import androidx.lifecycle.viewModelScope
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.domain.model.request.AdditionalIdentificationRequest
import com.app.freightCyber.domain.model.request.DriverLicenseRequest
import com.app.freightCyber.domain.model.response.AdditionalIdentificationResponse
import com.app.freightCyber.domain.model.response.DriverLicenseResponse
import com.app.freightCyber.domain.usecase.DriverLicenseUseCase
import com.app.freightCyber.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LicenseDetailActivityVM @Inject constructor(private val driverLicenseUseCase: DriverLicenseUseCase): BaseViewModel() {

    private val _driverLicenseResult: MutableStateFlow<NetworkResult<DriverLicenseResponse>?> =
        MutableStateFlow(null)
    val driverLicenseResult: StateFlow<NetworkResult<DriverLicenseResponse>?> get() = _driverLicenseResult
    fun createDriverLicense(driverLicenseRequest: DriverLicenseRequest) {
        viewModelScope.launch {
            driverLicenseUseCase.invoke(driverLicenseRequest)
                .catch { e ->
                    _driverLicenseResult.value = NetworkResult.Error(e)
                }.collect {
                    _driverLicenseResult.value = it
                }
        }
    }

    fun resetUpdateDriverLicenseResult() {
        _driverLicenseResult.value = null
    }
}