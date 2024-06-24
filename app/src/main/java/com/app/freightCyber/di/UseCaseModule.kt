package com.app.freightCyber.di

import com.app.freightCyber.domain.repository.AuthRepository
import com.app.freightCyber.domain.usecase.AdditionalIdentificationUseCase
import com.app.freightCyber.domain.usecase.DriverLicenseUseCase
import com.app.freightCyber.domain.usecase.DriverProfilePreSignedUrlUseCase
import com.app.freightCyber.domain.usecase.DriverProfileUseCase
import com.app.freightCyber.domain.usecase.EmergencyContactUseCase
import com.app.freightCyber.domain.usecase.GeneratePasscodeUseCase
import com.app.freightCyber.domain.usecase.GetAdditionalIdentificationUseCase
import com.app.freightCyber.domain.usecase.GetAgreementUseCase
import com.app.freightCyber.domain.usecase.GetDriverLicenseUseCase
import com.app.freightCyber.domain.usecase.GetEmergencyContactUseCase
import com.app.freightCyber.domain.usecase.LoginUseCase
import com.app.freightCyber.domain.usecase.SignUpUseCase
import com.app.freightCyber.domain.usecase.GetTransportOperatorUseCase
import com.app.freightCyber.domain.usecase.TransportOperatorUseCase
import com.app.freightCyber.domain.usecase.UpdateAdditionalIdentificationUseCase
import com.app.freightCyber.domain.usecase.UpdateAgreementUseCase
import com.app.freightCyber.domain.usecase.UpdateDriverProfileUseCase
import com.app.freightCyber.domain.usecase.UpdateEmergencyContactUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGeneratePassCodeUseCase(authRepository: AuthRepository): GeneratePasscodeUseCase {
        return GeneratePasscodeUseCase(authRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSignUpUseCase(authRepository: AuthRepository): SignUpUseCase {
        return SignUpUseCase(authRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideEmergencyContactUseCase(authRepository: AuthRepository): EmergencyContactUseCase {
        return EmergencyContactUseCase(authRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideIdentificationUseCase(authRepository: AuthRepository): AdditionalIdentificationUseCase {
        return AdditionalIdentificationUseCase(authRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideDriverProfileUseCase(authRepository: AuthRepository): DriverProfileUseCase {
        return DriverProfileUseCase(authRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideUpdateDriverProfileUseCase(authRepository: AuthRepository): UpdateDriverProfileUseCase {
        return UpdateDriverProfileUseCase(authRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideDriverLicenseUseCase(authRepository: AuthRepository): DriverLicenseUseCase {
        return DriverLicenseUseCase(authRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetTransportOperatorUseCase(authRepository: AuthRepository): GetTransportOperatorUseCase {
        return GetTransportOperatorUseCase(authRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideTransportOperatorUseCase(authRepository: AuthRepository): TransportOperatorUseCase {
        return TransportOperatorUseCase(authRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetEmergencyContactUseCase(authRepository: AuthRepository): GetEmergencyContactUseCase {
        return GetEmergencyContactUseCase(authRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideUpdateEmergencyContactUseCase(authRepository: AuthRepository): UpdateEmergencyContactUseCase {
        return UpdateEmergencyContactUseCase(authRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetAdditionalIdentificationUseCase(authRepository: AuthRepository): GetAdditionalIdentificationUseCase {
        return GetAdditionalIdentificationUseCase(authRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideUpdateAdditionalIdentificationUseCase(authRepository: AuthRepository): UpdateAdditionalIdentificationUseCase {
        return UpdateAdditionalIdentificationUseCase(authRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetDriverLicenseUseCase(authRepository: AuthRepository): GetDriverLicenseUseCase {
        return GetDriverLicenseUseCase(authRepository)
    }
    @Provides
    @ViewModelScoped
    fun provideGetAgreementUseCase(authRepository: AuthRepository): GetAgreementUseCase {
        return GetAgreementUseCase(authRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideUpdateAgreementUseCase(authRepository: AuthRepository): UpdateAgreementUseCase {
        return UpdateAgreementUseCase(authRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideDriverProfilePreSignedUrl(authRepository: AuthRepository): DriverProfilePreSignedUrlUseCase {
        return DriverProfilePreSignedUrlUseCase(authRepository)
    }
}