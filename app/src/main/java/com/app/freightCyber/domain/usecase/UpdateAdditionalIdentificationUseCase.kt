package com.app.freightCyber.domain.usecase

import com.app.freightCyber.domain.model.request.AdditionalIdentificationRequest
import com.app.freightCyber.domain.model.response.AdditionalIdentificationResponse
import com.app.freightCyber.domain.repository.AuthRepository
import com.app.freightCyber.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateAdditionalIdentificationUseCase @Inject constructor(private val authRepository: AuthRepository){

    suspend operator fun invoke(additionalIdentificationRequest: AdditionalIdentificationRequest): Flow<NetworkResult<AdditionalIdentificationResponse>> {
        return authRepository.updateAdditionalIdentification(additionalIdentificationRequest)
    }
}