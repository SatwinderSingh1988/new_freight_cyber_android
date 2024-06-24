package com.app.freightCyber.domain.usecase

import com.app.freightCyber.domain.model.request.EmergencyContactRequest
import com.app.freightCyber.domain.model.response.EmergencyContactResponse
import com.app.freightCyber.domain.repository.AuthRepository
import com.app.freightCyber.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateEmergencyContactUseCase @Inject constructor(private val authRepository: AuthRepository){

    suspend operator fun invoke(emergencyContactRequest: EmergencyContactRequest): Flow<NetworkResult<EmergencyContactResponse>> {
        return authRepository.emergencyContact(emergencyContactRequest)
    }
}