package com.example.studyapp.database.dao

import androidx.room.*
import com.example.studyapp.database.entity.TaskEntity

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getAll(): List<TaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: TaskEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(users: TaskEntity): Int

    @Delete
    fun delete(model: TaskEntity?)

    //select * ~ select id, title, content
//    @Query("SELECT title, id, content FROM task WHERE title LIKE '%'||:title||'%'")
    @Query("SELECT * FROM task WHERE title LIKE '%'||:data||'%'")
    fun findTaskWithName(data: String): List<TaskEntity>
}