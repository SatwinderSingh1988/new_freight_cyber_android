package com.app.freightCyber.data.repository

import com.app.freightCyber.core.network.repository.BaseRepository
import com.app.freightCyber.data.source.remote.ApiAuthService
import com.app.freightCyber.domain.model.request.AdditionalIdentificationRequest
import com.app.freightCyber.domain.model.request.AgreementRequest
import com.app.freightCyber.domain.model.request.DriverLicenseRequest
import com.app.freightCyber.domain.model.request.DriverProfilePreSignedUrlRequest
import com.app.freightCyber.domain.model.request.DriverProfileRequest
import com.app.freightCyber.domain.model.request.EmergencyContactRequest
import com.app.freightCyber.domain.model.request.GeneratePasscodeRequest
import com.app.freightCyber.domain.model.request.LoginRequest
import com.app.freightCyber.domain.model.request.SignUpRequest
import com.app.freightCyber.domain.model.request.TransportOperatorRequest
import com.app.freightCyber.domain.model.response.AdditionalIdentification
import com.app.freightCyber.domain.model.response.AdditionalIdentificationResponse
import com.app.freightCyber.domain.model.response.DriverLicenseDetailsResponse
import com.app.freightCyber.domain.model.response.DriverLicenseResponse
import com.app.freightCyber.domain.model.response.DriverProfileResponse
import com.app.freightCyber.domain.model.response.EmergencyContactResponse
import com.app.freightCyber.domain.model.response.GetAdditionalIdentificationResponse
import com.app.freightCyber.domain.model.response.LoginSignUpResponse
import com.app.freightCyber.domain.model.response.SimpleApiResponse
import com.app.freightCyber.domain.model.response.TransportOperatorResponse
import com.app.freightCyber.domain.repository.AuthRepository
import com.app.freightCyber.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val apiAuthService: ApiAuthService) :
    AuthRepository, BaseRepository() {

    override suspend fun generatePasscode(generatePasscode: GeneratePasscodeRequest): Flow<NetworkResult<SimpleApiResponse>> {
        return makeNetworkRequest(generatePasscode, apiAuthService::generatePasscode)
    }

    override suspend fun signup(signUpRequest: SignUpRequest): Flow<NetworkResult<LoginSignUpResponse>> =
        makeNetworkRequest(signUpRequest, apiAuthService::signup)


    override suspend fun login(loginRequest: LoginRequest): Flow<NetworkResult<LoginSignUpResponse>> =
        makeNetworkRequest(loginRequest, apiAuthService::login)


    override suspend fun emergencyContact(emergencyContactRequest: EmergencyContactRequest): Flow<NetworkResult<EmergencyContactResponse>> {
        return makeNetworkRequest(emergencyContactRequest, apiAuthService::emergencyContact)
    }

    override suspend fun additionalIdentification(additionalIdentificationRequest: AdditionalIdentificationRequest): Flow<NetworkResult<AdditionalIdentificationResponse>> {
        return makeNetworkRequest(additionalIdentificationRequest, apiAuthService::additionalIdentification)
    }

    override suspend fun getDriverProfile(): Flow<NetworkResult<DriverProfileResponse>> {
        return makeNetworkRequest(Unit) { apiAuthService.getDriverProfile() }
    }

    override suspend fun updateDriverProfile(driverProfileRequest: DriverProfileRequest): Flow<NetworkResult<DriverProfileResponse>> {
        return makeNetworkRequest(driverProfileRequest , apiAuthService::updateDriverProfile)
    }

    override suspend fun createDriverLicense(driverLicenseRequest: DriverLicenseRequest): Flow<NetworkResult<DriverLicenseResponse>> {
        return makeNetworkRequest(driverLicenseRequest, apiAuthService::createDriverLicense)
    }

    override suspend fun getTransportOperator(): Flow<NetworkResult<TransportOperatorResponse>> {
        return makeNetworkRequest(Unit) { apiAuthService.getTransportOperator() }
    }

    override suspend fun transportOperator(transportOperatorRequest: TransportOperatorRequest): Flow<NetworkResult<TransportOperatorResponse>> {
        return makeNetworkRequest(transportOperatorRequest, apiAuthService::transportOperator)
    }

    override suspend fun getEmergencyContact(): Flow<NetworkResult<EmergencyContactResponse>> {
        return makeNetworkRequest(Unit) { apiAuthService.getEmergencyContact() }
    }

    override suspend fun updateEmergencyContact(emergencyContactRequest: EmergencyContactRequest): Flow<NetworkResult<EmergencyContactResponse>> {
        return makeNetworkRequest(emergencyContactRequest, apiAuthService::updateEmergencyContact)
    }

    override suspend fun getAdditionalIdentification(): Flow<NetworkResult<GetAdditionalIdentificationResponse>> {
        return makeNetworkRequest(Unit) { apiAuthService.getAdditionalIdentification() }
    }

    override suspend fun updateAdditionalIdentification(additionalIdentificationRequest: AdditionalIdentificationRequest): Flow<NetworkResult<AdditionalIdentificationResponse>> {
        return makeNetworkRequest(additionalIdentificationRequest, apiAuthService::updateAdditionalIdentification)
    }

    override suspend fun getLicenseDetails(): Flow<NetworkResult<DriverLicenseDetailsResponse>> {
        return makeNetworkRequest(Unit) { apiAuthService.getLicenseDetails() }
    }

    override suspend fun getAgreement(): Flow<NetworkResult<SimpleApiResponse>> {
        return makeNetworkRequest(Unit) { apiAuthService.getAgreement() }
    }

    override suspend fun updateAgreement(agreementRequest : AgreementRequest): Flow<NetworkResult<SimpleApiResponse>> {
        return makeNetworkRequest(agreementRequest , apiAuthService::updateAgreement)
    }

    override suspend fun driverProfilePresignedUrl(driverProfilePreSignedUrlRequest: DriverProfilePreSignedUrlRequest): Flow<NetworkResult<SimpleApiResponse>> {
       return makeNetworkRequest(driverProfilePreSignedUrlRequest , apiAuthService::driverProfilePresignedUrl)
    }


}