package com.app.freightCyber.presentation.home_dashboard.settings.profile_setting

import androidx.lifecycle.viewModelScope
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.domain.model.request.DriverProfileRequest
import com.app.freightCyber.domain.model.response.DriverProfileResponse
import com.app.freightCyber.domain.usecase.DriverProfileUseCase
import com.app.freightCyber.domain.usecase.UpdateDriverProfileUseCase
import com.app.freightCyber.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileSettingsFragmentVM @Inject constructor(
    private val driverProfileUseCase: DriverProfileUseCase,
    private val updateDriverProfileUseCase: UpdateDriverProfileUseCase,
): BaseViewModel() {


    private val _driverProfileResult = MutableStateFlow<NetworkResult<DriverProfileResponse>?>(null)
    val driverProfileResult: StateFlow<NetworkResult<DriverProfileResponse>?> get() = _driverProfileResult

    init {
        driverProfile()
    }
    private fun driverProfile() {
        viewModelScope.launch {
            driverProfileUseCase.invoke()
                .catch { e ->
                    _driverProfileResult.value = NetworkResult.Error(e)
                }
                .collect { result ->
                    _driverProfileResult.value = result
                }
        }
    }

    fun resetAndUpdateDriverProfile(){
        _driverProfileResult.value = null
    }



    private val _updateDriverProfileResult = MutableStateFlow<NetworkResult<DriverProfileResponse>?>(null)
    val updateDriverProfileResult: StateFlow<NetworkResult<DriverProfileResponse>?> get() = _updateDriverProfileResult

    fun updateDriverProfile(driverProfileRequest: DriverProfileRequest) {
        viewModelScope.launch {
            updateDriverProfileUseCase.invoke(driverProfileRequest)
                .catch { e ->
                    _updateDriverProfileResult.value = NetworkResult.Error(e)
                }
                .collect { result ->
                    _updateDriverProfileResult.value = result
                }
        }
    }

}