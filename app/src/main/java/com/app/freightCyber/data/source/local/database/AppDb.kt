package com.app.freightCyber.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.freightCyber.domain.model.dummy_data.DummyDbModel
import com.app.freightCyber.data.source.local.database.dao.RoomDataBaseQueryClass

@Database(entities = [(DummyDbModel::class)], version = 1)
abstract class AppDb : RoomDatabase() {

    abstract fun roomDataBaseQueryClass(): RoomDataBaseQueryClass

    companion object {
        const val DATA_BASE_NAME = "FRIEGHT_CYBER"
        const val DATA_BASE_VERSION = 1
    }
}