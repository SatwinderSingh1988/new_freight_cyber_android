package com.app.freightCyber.domain.usecase

import com.app.freightCyber.domain.model.request.DriverLicenseRequest
import com.app.freightCyber.domain.model.response.DriverLicenseResponse
import com.app.freightCyber.domain.repository.AuthRepository
import com.app.freightCyber.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DriverLicenseUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(driverLicenseRequest: DriverLicenseRequest): Flow<NetworkResult<DriverLicenseResponse>> {
        return authRepository.createDriverLicense(driverLicenseRequest)
    }
}