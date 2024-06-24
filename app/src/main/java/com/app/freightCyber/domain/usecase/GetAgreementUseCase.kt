package com.app.freightCyber.domain.usecase

import com.app.freightCyber.domain.model.response.SimpleApiResponse
import com.app.freightCyber.domain.repository.AuthRepository
import com.app.freightCyber.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

class GetAgreementUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(): Flow<NetworkResult<SimpleApiResponse>> {
        return authRepository.getAgreement()
    }
}