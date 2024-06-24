package com.app.freightCyber.domain.usecase

import com.app.freightCyber.domain.model.request.TransportOperatorRequest
import com.app.freightCyber.domain.model.response.TransportOperatorResponse
import com.app.freightCyber.domain.repository.AuthRepository
import com.app.freightCyber.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransportOperatorUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(transportOperatorRequest: TransportOperatorRequest): Flow<NetworkResult<TransportOperatorResponse>> {
        return authRepository.transportOperator(transportOperatorRequest)
    }
}