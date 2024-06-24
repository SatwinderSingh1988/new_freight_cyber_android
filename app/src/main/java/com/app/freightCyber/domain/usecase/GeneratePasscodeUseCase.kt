package com.app.freightCyber.domain.usecase

import com.app.freightCyber.domain.model.request.GeneratePasscodeRequest
import com.app.freightCyber.domain.model.response.SimpleApiResponse
import com.app.freightCyber.domain.repository.AuthRepository
import com.app.freightCyber.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

class GeneratePasscodeUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(generatePasscode: GeneratePasscodeRequest): Flow<NetworkResult<SimpleApiResponse>> {
        return authRepository.generatePasscode(generatePasscode)
    }
}