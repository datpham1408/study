package com.example.studyapp.adapter.diff_callback

import androidx.recyclerview.widget.DiffUtil
import com.example.studyapp.Model.MiniAppModel
import com.example.studyapp.Model.UserDanhBa

class DiffCallbackMiniApp: DiffUtil.ItemCallback <MiniAppModel>() {
    override fun areItemsTheSame(oldItem: MiniAppModel, newItem: MiniAppModel): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: MiniAppModel, newItem: MiniAppModel): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}