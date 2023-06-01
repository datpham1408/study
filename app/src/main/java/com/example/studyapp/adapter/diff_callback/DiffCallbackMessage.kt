package com.example.studyapp.adapter.diff_callback

import androidx.recyclerview.widget.DiffUtil
import com.example.studyapp.Model.MessageModel

class DiffCallbackMessage :DiffUtil.ItemCallback<MessageModel>() {
    override fun areItemsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}