package com.app.freightCyber.data.source.remote

import com.app.freightCyber.core.network.RequiresAuthorization
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
import com.app.freightCyber.utils.Constants
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part


interface ApiAuthService {

    @POST(Constants.GENERATE_PASSCODE)
    suspend fun generatePasscode(@Body generatePasscode: GeneratePasscodeRequest): Response<SimpleApiResponse>

    @POST(Constants.SIGNUP)
    suspend fun signup(@Body signupRequest: SignUpRequest): Response<LoginSignUpResponse>

    @POST(Constants.LOGIN)
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginSignUpResponse>

    @RequiresAuthorization
    @POST(Constants.EMERGENCY_CONTACT)
    suspend fun emergencyContact(
        @Body emergencyContact: EmergencyContactRequest
    ): Response<EmergencyContactResponse>

    @RequiresAuthorization
    @POST(Constants.ADDITIONAL_IDENTIFICATION)
    suspend fun additionalIdentification(
        @Body additionalIdentification: AdditionalIdentificationRequest
    ): Response<AdditionalIdentificationResponse>


    @RequiresAuthorization
    @GET(Constants.DRIVER_PROFILE)
    suspend fun getDriverProfile(): Response<DriverProfileResponse>


    @RequiresAuthorization
    @PUT(Constants.DRIVER_PROFILE)
    suspend fun updateDriverProfile(
        @Body driverProfileRequest: DriverProfileRequest,
    ): Response<DriverProfileResponse>


    @RequiresAuthorization
    @POST(Constants.DRIVER_LICENSE)
    suspend fun createDriverLicense(
        @Body driverLicenseRequest: DriverLicenseRequest
    ): Response<DriverLicenseResponse>


    @RequiresAuthorization
    @GET(Constants.TRANSPORT_OPERATOR)
    suspend fun getTransportOperator(): Response<TransportOperatorResponse>

    @RequiresAuthorization
    @POST(Constants.TRANSPORT_OPERATOR)
    suspend fun transportOperator(
        @Body transportOperatorRequest: TransportOperatorRequest
    ): Response<TransportOperatorResponse>


    @RequiresAuthorization
    @GET(Constants.EMERGENCY_CONTACT)
    suspend fun getEmergencyContact(): Response<EmergencyContactResponse>

    @RequiresAuthorization
    @PUT(Constants.EMERGENCY_CONTACT)
    suspend fun updateEmergencyContact(
        @Body emergencyContact: EmergencyContactRequest
    ): Response<EmergencyContactResponse>


    @RequiresAuthorization
    @GET(Constants.ADDITIONAL_IDENTIFICATION)
    suspend fun getAdditionalIdentification(
    ): Response<GetAdditionalIdentificationResponse>

    @RequiresAuthorization
    @PUT(Constants.ADDITIONAL_IDENTIFICATION)
    suspend fun updateAdditionalIdentification(
        @Body additionalIdentification: AdditionalIdentificationRequest
    ): Response<AdditionalIdentificationResponse>

    @RequiresAuthorization
    @GET(Constants.DRIVER_LICENSE)
    suspend fun getLicenseDetails() : Response<DriverLicenseDetailsResponse>

    @RequiresAuthorization
    @GET(Constants.DRIVER_AGREEMENT)
    suspend fun getAgreement() : Response<SimpleApiResponse>

    @RequiresAuthorization
    @PUT(Constants.DRIVER_AGREEMENT)
    suspend fun updateAgreement(
        agreementRequest : AgreementRequest
    ) : Response<SimpleApiResponse>

    @RequiresAuthorization
    @POST(Constants.DRIVER_PROFILE_PRESIGNED_URL)
    fun driverProfilePresignedUrl(
        @Body driverProfilePreSignedUrlRequest: DriverProfilePreSignedUrlRequest
    ) : Response<SimpleApiResponse>

}