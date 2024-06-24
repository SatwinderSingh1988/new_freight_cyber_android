package com.app.freightCyber.presentation.home_dashboard.driver_profile.license

import androidx.lifecycle.viewModelScope
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.domain.model.response.DriverLicenseDetailsResponse
import com.app.freightCyber.domain.usecase.GetDriverLicenseUseCase
import com.app.freightCyber.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LicenseInfoFirstFragmentVM @Inject constructor(private val useCase : GetDriverLicenseUseCase): BaseViewModel() {

    private val _driverLicenseResult = MutableStateFlow<NetworkResult<DriverLicenseDetailsResponse>?>(null)
    val driverLicenseResult: StateFlow<NetworkResult<DriverLicenseDetailsResponse>?> get() = _driverLicenseResult

    init {
        getDriverLicense()
    }
    private fun getDriverLicense() {
        viewModelScope.launch {
            useCase.invoke()
                .catch { e ->
                    _driverLicenseResult.value = NetworkResult.Error(e)
                }
                .collect { result ->
                    _driverLicenseResult.value = result
                }
        }
    }
}