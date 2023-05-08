package com.example.studyapp

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.studyapp.database.dao.UserDao
import com.example.studyapp.database.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}