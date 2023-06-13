package com.example.studyapp.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @ColumnInfo(name = "email") var email: String?=null,
    @ColumnInfo(name = "password") var password: String?=null,
    @ColumnInfo(name = "xac nhan password") var xacNhanPassword: String?=null,
    @ColumnInfo(name = "tuoi") var age: String?=null,
    @ColumnInfo(name = "sdt") var phone: String?=null,
    @ColumnInfo(name = "uid") var uid : String?=null
) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

