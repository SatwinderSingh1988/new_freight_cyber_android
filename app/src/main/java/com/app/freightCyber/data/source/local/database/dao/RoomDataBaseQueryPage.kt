package com.app.freightCyber.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.freightCyber.domain.model.dummy_data.DummyDbModel

@Dao
interface RoomDataBaseQueryClass
{
    @Insert
    fun saveData(book: DummyDbModel)
    @Query(value = "Select * from DummyDbModel")
    fun getAllData() : List<DummyDbModel>
}