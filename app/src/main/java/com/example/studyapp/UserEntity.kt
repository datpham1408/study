package com.example.studyapp

import androidx.room.*

data class User(
    val id: Int,
    val email: String,
    val password: String
){

    fun temp(){
        val userEntity = UserEntity(id, email, password)
        userEntity.email

    }


}

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "email_info") val email: String,
    @ColumnInfo(name = "password_info") val password: String
)

@Entity
data class InfoEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "email_info") val email: String,
    @ColumnInfo(name = "password_info") val password: String
)

@Dao
interface UserDao{
    @Query("SELECT * FROM user")
    fun getAll(): List<UserEntity>
//
//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<UserEntity>
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): UserEntity

    @Insert
    fun insertAll(vararg users: UserEntity)

//    @Delete
//    fun delete(user: UserEntity)
}

@Database(entities = [UserEntity::class,InfoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
