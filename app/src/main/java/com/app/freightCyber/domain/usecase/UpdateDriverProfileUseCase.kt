package com.app.freightCyber.domain.usecase

import com.app.freightCyber.domain.model.request.DriverProfileRequest
import com.app.freightCyber.domain.model.response.DriverProfileResponse
import com.app.freightCyber.domain.repository.AuthRepository
import com.app.freightCyber.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateDriverProfileUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(driverProfileRequest: DriverProfileRequest): Flow<NetworkResult<DriverProfileResponse>> {
        return authRepository.updateDriverProfile(driverProfileRequest)
    }

}