package com.example.studyapp.adapter.diff_callback

import androidx.recyclerview.widget.DiffUtil
import com.example.studyapp.Model.UserDanhBa

class DiffCallbackDanhBa: DiffUtil.ItemCallback <UserDanhBa>() {
    override fun areItemsTheSame(oldItem: UserDanhBa, newItem: UserDanhBa): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: UserDanhBa, newItem: UserDanhBa): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}