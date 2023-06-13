package com.example.studyapp.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.studyapp.Model.MessageModel
import com.example.studyapp.Model.UserDanhBa


@Entity(tableName = "friend")
data class FriendEntity(
    @ColumnInfo(name = "user_name") var userName: String,
    @ColumnInfo(name = "nick_name") var nickName: String,
    @ColumnInfo(name = "id_user") var idUser: Int,
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    fun convertToModel():MessageModel{

        return MessageModel(userName = userName, nickName = nickName)
    }
    fun convertToUserDanhBaModel(): UserDanhBa {
        return UserDanhBa(image = 0 , name = userName)

    }
}
