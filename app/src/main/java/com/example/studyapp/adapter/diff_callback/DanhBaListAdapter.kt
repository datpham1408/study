package com.example.studyapp.adapter.diff_callback

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studyapp.Model.UserDanhBa
import com.example.studyapp.databinding.ItemDanhBaBinding

class DanhBaListAdapter :
    ListAdapter<UserDanhBa, DanhBaListAdapter.DanhBaViewHolder>(DiffCallbackDanhBa()) {

    inner class DanhBaViewHolder(private var binding: ItemDanhBaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun initData(userDanhBa: UserDanhBa) {
            binding.tvAvatar.text = userDanhBa.name.first().toString().uppercase()
            binding.tvName.text = userDanhBa.name

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DanhBaViewHolder {

        var binding = ItemDanhBaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DanhBaViewHolder(binding)


    }

    override fun onBindViewHolder(holder: DanhBaViewHolder, position: Int) {
        var user = getItem(position)

        holder.initData(user)

    }
}