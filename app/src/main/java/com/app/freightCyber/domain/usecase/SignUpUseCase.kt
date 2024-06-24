package com.app.freightCyber.domain.usecase

import com.app.freightCyber.domain.model.request.SignUpRequest
import com.app.freightCyber.domain.model.response.LoginSignUpResponse
import com.app.freightCyber.domain.repository.AuthRepository
import com.app.freightCyber.utils.NetworkResult
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow

class SignUpUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(signUpRequest: SignUpRequest): Flow<NetworkResult<LoginSignUpResponse>> {
        return authRepository.signup(signUpRequest)
    }

}