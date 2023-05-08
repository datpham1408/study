package com.example.studyapp.adapter.diff_callback

import androidx.recyclerview.widget.DiffUtil
import com.example.studyapp.database.entity.UserEntity

class DiffcallBackSubject : DiffUtil.ItemCallback<UserEntity>() {
    override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}