package com.app.freightCyber.domain.usecase

import com.app.freightCyber.domain.model.request.DriverProfilePreSignedUrlRequest
import com.app.freightCyber.domain.model.response.SimpleApiResponse
import com.app.freightCyber.domain.repository.AuthRepository
import com.app.freightCyber.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DriverProfilePreSignedUrlUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(driverProfilePresignedUrlRequest: DriverProfilePreSignedUrlRequest) : Flow<NetworkResult<SimpleApiResponse>> {
     return   authRepository.driverProfilePresignedUrl(driverProfilePresignedUrlRequest)
    }
}