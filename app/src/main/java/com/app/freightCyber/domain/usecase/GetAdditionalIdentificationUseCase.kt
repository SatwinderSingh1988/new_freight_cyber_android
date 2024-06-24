package com.app.freightCyber.domain.usecase

import com.app.freightCyber.domain.model.response.AdditionalIdentification
import com.app.freightCyber.domain.model.response.AdditionalIdentificationResponse
import com.app.freightCyber.domain.model.response.GetAdditionalIdentificationResponse
import com.app.freightCyber.domain.repository.AuthRepository
import com.app.freightCyber.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAdditionalIdentificationUseCase @Inject constructor(private val authRepository: AuthRepository){

    suspend operator fun invoke(): Flow<NetworkResult<GetAdditionalIdentificationResponse>> {
        return authRepository.getAdditionalIdentification()
    }
}