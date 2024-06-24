package com.app.freightCyber.presentation.auth.verify_otp

import androidx.lifecycle.viewModelScope
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.domain.model.request.LoginRequest
import com.app.freightCyber.domain.model.request.SignUpRequest
import com.app.freightCyber.domain.model.response.LoginSignUpResponse
import com.app.freightCyber.domain.usecase.LoginUseCase
import com.app.freightCyber.domain.usecase.SignUpUseCase
import com.app.freightCyber.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyOtpActivityVM @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val loginUseCase: LoginUseCase
) :
    BaseViewModel() {

    private val _signupResult = MutableStateFlow<NetworkResult<LoginSignUpResponse>?>(null)
    val signupResult: StateFlow<NetworkResult<LoginSignUpResponse>?> get() = _signupResult
    fun signup(signUpRequest: SignUpRequest) {
        viewModelScope.launch {
            signUpUseCase.invoke(signUpRequest)
                .catch { e ->
                    _signupResult.value = NetworkResult.Error(e)
                }.collect { result ->
                    _signupResult.value = result
                }
        }
    }

    private val _loginResult = MutableStateFlow<NetworkResult<LoginSignUpResponse>?>(null)
    val loginResult: StateFlow<NetworkResult<LoginSignUpResponse>?> get() = _loginResult

    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            loginUseCase.invoke(loginRequest)
                .catch { e ->
                    _loginResult.value = NetworkResult.Error(e)
                }.collect {
                    _loginResult.value = it
                }
        }
    }

    fun resetUpdateSignUpResult() {
        _signupResult.value = null
    }

    fun resetUpdateLoginResult() {
        _loginResult.value = null
    }

}