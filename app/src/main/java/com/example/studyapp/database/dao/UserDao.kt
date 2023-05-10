package com.example.studyapp.database.dao

import androidx.room.*
import com.example.studyapp.database.entity.UserEntity


@Dao
interface UserDao{
    @Query("SELECT * FROM user")
    fun getAll(): List<UserEntity>

    @Insert
    fun insertAll(vararg users: UserEntity)

    @Update
    fun updateUser(vararg users: UserEntity)

    @Delete
    fun delete(model: UserEntity?)


}
