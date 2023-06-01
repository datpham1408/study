package com.example.studyapp.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "password") var password: String,
    @ColumnInfo(name = "xac nhan password") var xacNhanPassword: String,
) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

