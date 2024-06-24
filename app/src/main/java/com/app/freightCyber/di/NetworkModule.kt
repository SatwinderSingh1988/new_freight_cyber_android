package com.app.freightCyber.di

import android.content.Context
import com.app.freightCyber.core.network.ApiClient
import com.app.freightCyber.core.network.serlializer.DateSerializer
import com.app.freightCyber.data.source.local.prefrences.SharedPrefManager
import com.app.freightCyber.data.source.remote.ApiAuthService
import com.app.freightCyber.data.source.remote.ApiDashboardService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import java.util.Date
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideApiClient(
        @ApplicationContext appContext: Context, gson: Gson, sharedPreferences: SharedPrefManager) = ApiClient(appContext, gson, sharedPreferences).getApiClient()

    @Provides
    @Singleton
    fun provideApiAuthService(retrofit: Retrofit): ApiAuthService = retrofit.create(ApiAuthService::class.java)

    @Provides
    @Singleton
    fun provideApiDashboardService(retrofit: Retrofit): ApiDashboardService = retrofit.create(ApiDashboardService::class.java)

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder()
            .registerTypeAdapter(Date::class.java, DateSerializer())
            .create()

}