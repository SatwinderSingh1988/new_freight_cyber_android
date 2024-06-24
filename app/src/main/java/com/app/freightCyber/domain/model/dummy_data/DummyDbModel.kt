package com.app.freightCyber.domain.model.dummy_data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class DummyDbModel {
    @PrimaryKey(autoGenerate = true)
    var Id: Int =0
    @ColumnInfo (name ="BookName")
    var name:  String =""
}