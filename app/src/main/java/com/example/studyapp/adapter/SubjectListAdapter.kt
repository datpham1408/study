package com.example.studyapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studyapp.adapter.diff_callback.DiffcallBackSubject
import com.example.studyapp.database.entity.UserEntity
import com.example.studyapp.databinding.ItemSubjectBinding
import com.example.studyapp.dialog.DialogDeleteUser


class SubjectListAdapter(
    val onClickView: (UserEntity) -> Unit,
    val onClickDelete: (UserEntity) -> Unit,
    val onClickEdit: (UserEntity) -> Unit
) :
    ListAdapter<UserEntity, SubjectListAdapter.SubjectViewHolder>(DiffcallBackSubject()) {
    inner class SubjectViewHolder(private var binding: ItemSubjectBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(entity: UserEntity) {
            binding.tvId.text = entity.id.toString()
            binding.tvTen.text = entity.ten
            binding.tvTuoi.text = entity.tuoi.toString()
            binding.tvNamSinh.text = entity.namSinh.toString()


            binding.btXem.setOnClickListener {
                onClickView(entity)
            }
            binding.btSua.setOnClickListener {
                onClickEdit(entity)
            }
            binding.btXoa.setOnClickListener {
                onClickDelete(entity)

            }


        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {

        val binding =
            ItemSubjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {

        val subject = getItem(position)
        holder.bindData(subject)

    }
}