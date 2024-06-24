package com.app.freightCyber.domain.repository

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
import com.app.freightCyber.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart

interface AuthRepository {
    suspend fun generatePasscode(generatePasscode: GeneratePasscodeRequest): Flow<NetworkResult<SimpleApiResponse>>

    suspend fun signup(signUpRequest: SignUpRequest): Flow<NetworkResult<LoginSignUpResponse>>

    suspend fun login(loginRequest: LoginRequest): Flow<NetworkResult<LoginSignUpResponse>>

    suspend fun emergencyContact(emergencyContactRequest: EmergencyContactRequest): Flow<NetworkResult<EmergencyContactResponse>>

    suspend fun additionalIdentification(additionalIdentificationRequest: AdditionalIdentificationRequest): Flow<NetworkResult<AdditionalIdentificationResponse>>

    suspend fun getDriverProfile(): Flow<NetworkResult<DriverProfileResponse>>

    suspend fun updateDriverProfile(driverProfileRequest: DriverProfileRequest): Flow<NetworkResult<DriverProfileResponse>>

    suspend fun createDriverLicense(driverLicenseRequest: DriverLicenseRequest): Flow<NetworkResult<DriverLicenseResponse>>

    suspend fun getTransportOperator(): Flow<NetworkResult<TransportOperatorResponse>>
    suspend fun transportOperator(transportOperatorRequest: TransportOperatorRequest): Flow<NetworkResult<TransportOperatorResponse>>

    suspend fun getEmergencyContact(): Flow<NetworkResult<EmergencyContactResponse>>
    suspend fun updateEmergencyContact(emergencyContactRequest: EmergencyContactRequest): Flow<NetworkResult<EmergencyContactResponse>>

    suspend fun getAdditionalIdentification(): Flow<NetworkResult<GetAdditionalIdentificationResponse>>
    suspend fun updateAdditionalIdentification(additionalIdentificationRequest: AdditionalIdentificationRequest): Flow<NetworkResult<AdditionalIdentificationResponse>>

    suspend fun getLicenseDetails(): Flow<NetworkResult<DriverLicenseDetailsResponse>>
    suspend fun getAgreement(): Flow<NetworkResult<SimpleApiResponse>>
    suspend fun updateAgreement(agreementRequest : AgreementRequest): Flow<NetworkResult<SimpleApiResponse>>

    suspend fun driverProfilePresignedUrl(driverProfilePreSignedUrlRequest: DriverProfilePreSignedUrlRequest): Flow<NetworkResult<SimpleApiResponse>>




}