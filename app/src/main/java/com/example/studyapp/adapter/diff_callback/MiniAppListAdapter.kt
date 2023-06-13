package com.example.studyapp.adapter.diff_callback

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studyapp.Model.MiniAppModel
import com.example.studyapp.Model.UserDanhBa
import com.example.studyapp.databinding.ItemDanhBaBinding
import com.example.studyapp.databinding.ItemMiniAppBinding
import kotlinx.coroutines.NonDisposableHandle.parent

class MiniAppListAdapter :
    ListAdapter<MiniAppModel, MiniAppListAdapter.MiniAppViewHolder>(DiffCallbackMiniApp()) {

    inner class MiniAppViewHolder(private var binding: ItemMiniAppBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun initData(miniAppModel: MiniAppModel) {
            binding.itemMiniAppIvTab.setImageResource(miniAppModel.image)
            binding.itemMiniAppTvTitle.text = miniAppModel.title

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MiniAppViewHolder {

        var binding = ItemMiniAppBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MiniAppViewHolder(binding)


    }

    override fun onBindViewHolder(holder: MiniAppViewHolder, position: Int) {
        var user = getItem(position)

        holder.initData(user)

    }
}