package com.app.freightCyber.di

import android.content.Context
import androidx.room.Room
import com.app.freightCyber.data.source.local.database.AppDb
import com.app.freightCyber.data.source.local.database.AppDb.Companion.DATA_BASE_NAME
import com.app.freightCyber.data.source.local.database.dao.RoomDataBaseQueryClass
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DatabaseModule {

    @Provides
    @ViewModelScoped
    fun provideDatabase(@ApplicationContext applicationContext: Context) =
        Room.databaseBuilder(applicationContext, AppDb::class.java, DATA_BASE_NAME).build()

    @Provides
    @ViewModelScoped
    fun provideMenuResponseDao(appDB: AppDb): RoomDataBaseQueryClass = appDB.roomDataBaseQueryClass()

}