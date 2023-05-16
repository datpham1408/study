package com.example.studyapp.model

import com.example.studyapp.database.entity.TaskEntity

@kotlinx.serialization.Serializable
class TaskModel(var title : String, var content : String, var id : Int) {

    fun convertToEntity():TaskEntity{
        var entity = TaskEntity(title, content)
        entity.id = id
        return entity
    }
}