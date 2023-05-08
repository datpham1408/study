package com.example.studyapp.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @ColumnInfo(name = "ten") val ten: String,
    @ColumnInfo(name = "tuoi") val tuoi: Int,
    @ColumnInfo(name = "nam_sinh") val namSinh: Int
) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}