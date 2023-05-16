package com.example.studyapp.adapter.diff_callback

import androidx.recyclerview.widget.DiffUtil
import com.example.studyapp.database.entity.TaskEntity
import com.example.studyapp.model.TaskModel

class DiffCallbackTask : DiffUtil.ItemCallback<TaskEntity>() {
    override fun areItemsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}