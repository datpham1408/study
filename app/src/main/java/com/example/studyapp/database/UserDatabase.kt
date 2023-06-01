package com.example.studyapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.studyapp.database.dao.FriendDao
import com.example.studyapp.database.dao.UserDao
import com.example.studyapp.database.entity.FriendEntity
import com.example.studyapp.database.entity.UserEntity

@Database(entities = [UserEntity::class, FriendEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun friendDao(): FriendDao


}