package com.example.studyapp.database.dao

import androidx.room.*
import com.example.studyapp.database.entity.FriendEntity

@Dao
interface FriendDao {
    @Query("SELECT * FROM friend WHERE id_user LIKE :idUser")
    fun getAllFriend(idUser : Int): List<FriendEntity>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFriendAll(vararg friends: FriendEntity)



    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateFriend(friends: FriendEntity): Int


    @Delete
    fun deleteFriend(model: FriendEntity?)

    @Query("SELECT * FROM friend WHERE user_name LIKE :userName ")
    fun findFriendWithUserName(userName: String): List<FriendEntity>
}