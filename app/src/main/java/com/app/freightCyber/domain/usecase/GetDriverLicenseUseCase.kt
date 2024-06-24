package com.app.freightCyber.domain.usecase

import com.app.freightCyber.domain.model.response.DriverLicenseDetailsResponse
import com.app.freightCyber.domain.repository.AuthRepository
import com.app.freightCyber.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDriverLicenseUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(): Flow<NetworkResult<DriverLicenseDetailsResponse>> {
        return authRepository.getLicenseDetails()
    }
}