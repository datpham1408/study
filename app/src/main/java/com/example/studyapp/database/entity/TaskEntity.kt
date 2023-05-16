package com.example.studyapp.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.studyapp.model.TaskModel

//@kotlinx.serialization.Serializable
@Entity(tableName = "task")
data class TaskEntity(
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "content") var content: String,


    ) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    fun convertToModel(): TaskModel {
        var model = TaskModel(title = title, content = content, id = id)
        return model

    }

}