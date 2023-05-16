package com.example.studyapp.database

import androidx.room.*
import com.example.studyapp.database.dao.TaskDao
import com.example.studyapp.database.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}