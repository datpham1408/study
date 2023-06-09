package com.example.studyapp.database.dao

import androidx.room.*

import com.example.studyapp.database.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<UserEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: UserEntity)


    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(users: UserEntity): Int


    @Delete
    fun delete(model: UserEntity?)

    @Query("SELECT * FROM user WHERE id LIKE :id")
    fun findUserById(id: Int): UserEntity


    @Query("SELECT * FROM user WHERE uid LIKE :uid")
    fun findUserByUId(uid: String?): UserEntity?

    @Query("SELECT * FROM user WHERE email LIKE :email")
    fun forgotPassword(email: String) :List<UserEntity>


    @Query("SELECT * FROM user WHERE email LIKE :email AND password LIKE:password")
    fun findUserWithName(email: String, password: String): List<UserEntity>


}