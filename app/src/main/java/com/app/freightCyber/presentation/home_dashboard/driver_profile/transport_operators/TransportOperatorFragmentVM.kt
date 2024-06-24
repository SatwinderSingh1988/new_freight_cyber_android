package com.app.freightCyber.presentation.home_dashboard.driver_profile.transport_operators

import androidx.lifecycle.viewModelScope
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.domain.model.request.TransportOperatorRequest
import com.app.freightCyber.domain.model.response.TransportOperatorResponse
import com.app.freightCyber.domain.usecase.GetTransportOperatorUseCase
import com.app.freightCyber.domain.usecase.TransportOperatorUseCase
import com.app.freightCyber.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransportOperatorFragmentVM @Inject constructor(
    private val getTransportOperatorUseCase: GetTransportOperatorUseCase ,
    private val transportOperatorUseCase: TransportOperatorUseCase
) : BaseViewModel() {


    private val _getTransportResult = MutableStateFlow<NetworkResult<TransportOperatorResponse>?>(null)
    val getTransportResult: StateFlow<NetworkResult<TransportOperatorResponse>?> get() = _getTransportResult

    init {
        getTransportData()
    }

    private fun getTransportData() {
        viewModelScope.launch {
            getTransportOperatorUseCase.invoke()
                .catch { e ->
                    _getTransportResult.value = NetworkResult.Error(e)
                }
                .collect { result ->
                    _getTransportResult.value = result
                }
        }
    }

    fun resetAndUpdateTransportOperatorResult() {
        _getTransportResult.value = null
    }


    private val _transportResult = MutableStateFlow<NetworkResult<TransportOperatorResponse>?>(null)
    val transportResult: StateFlow<NetworkResult<TransportOperatorResponse>?> get() = _transportResult

     fun postTransportData(transportOperatorRequest: TransportOperatorRequest) {
        viewModelScope.launch {
            transportOperatorUseCase.invoke(transportOperatorRequest)
                .catch { e ->
                    _transportResult.value = NetworkResult.Error(e)
                }
                .collect { result ->
                    _transportResult.value = result
                }
        }
    }

}