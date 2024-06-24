package com.app.freightCyber.domain.usecase

import com.app.freightCyber.domain.model.request.LoginRequest
import com.app.freightCyber.domain.model.response.LoginSignUpResponse
import com.app.freightCyber.domain.repository.AuthRepository
import com.app.freightCyber.utils.NetworkResult
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow

class LoginUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(loginRequest: LoginRequest): Flow<NetworkResult<LoginSignUpResponse>> {
        return authRepository.login(loginRequest)
    }

}