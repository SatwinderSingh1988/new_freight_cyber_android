package com.app.freightCyber.domain.usecase

import com.app.freightCyber.domain.model.request.AgreementRequest
import com.app.freightCyber.domain.model.response.SimpleApiResponse
import com.app.freightCyber.domain.repository.AuthRepository
import com.app.freightCyber.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

class UpdateAgreementUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(agreementRequest: AgreementRequest) : Flow<NetworkResult<SimpleApiResponse>> {
        return authRepository.updateAgreement(agreementRequest)
    }
}