package com.app.freightCyber.di

import com.app.freightCyber.data.repository.AuthRepositoryImpl
import com.app.freightCyber.data.repository.DashboardRepositoryImpl
import com.app.freightCyber.data.source.remote.ApiAuthService
import com.app.freightCyber.data.source.remote.ApiDashboardService
import com.app.freightCyber.domain.repository.AuthRepository
import com.app.freightCyber.domain.repository.DashboardRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideAuthRepository(apiAuthService: ApiAuthService): AuthRepository {
        return AuthRepositoryImpl(apiAuthService)
    }

    @Provides
    @ViewModelScoped
    fun provideDashboardRepository(apiDashboardService: ApiDashboardService): DashboardRepository {
        return DashboardRepositoryImpl(apiDashboardService)
    }


}