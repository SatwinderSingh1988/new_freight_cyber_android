package com.app.freightCyber.presentation.auth.personal_details

import androidx.lifecycle.viewModelScope
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.domain.model.request.DriverProfilePreSignedUrlRequest
import com.app.freightCyber.domain.model.request.DriverProfileRequest
import com.app.freightCyber.domain.model.response.DriverProfileResponse
import com.app.freightCyber.domain.model.response.SimpleApiResponse
import com.app.freightCyber.domain.usecase.DriverProfilePreSignedUrlUseCase
import com.app.freightCyber.domain.usecase.UpdateDriverProfileUseCase
import com.app.freightCyber.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalDetailActivityVM @Inject constructor(
    private val updateDriverProfileUseCase: UpdateDriverProfileUseCase ,
    private val provideDriverProfilePreSignedUrl:DriverProfilePreSignedUrlUseCase ,
): BaseViewModel() {

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

    fun resetUpdateDriverProfileResult() {
        _updateDriverProfileResult.value = null
    }


    private val _preSignedUrl = MutableStateFlow<NetworkResult<SimpleApiResponse>?>(null)
    val preSignedUrl: StateFlow<NetworkResult<SimpleApiResponse>?> get() = _preSignedUrl

    fun getDriverProfilePreSignedUrl(driverProfilePreSignedUrlRequest: DriverProfilePreSignedUrlRequest) {
        viewModelScope.launch {
            provideDriverProfilePreSignedUrl.invoke(driverProfilePreSignedUrlRequest)
                .catch { e ->
                    _preSignedUrl.value = NetworkResult.Error(e)
                }
                .collect { result ->
                    _preSignedUrl.value = result
                }
        }
    }
}