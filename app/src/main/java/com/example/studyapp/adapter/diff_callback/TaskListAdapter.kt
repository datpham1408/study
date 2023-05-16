package com.example.studyapp.adapter.diff_callback

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studyapp.database.entity.TaskEntity
import com.example.studyapp.databinding.ItemTaskBinding
import com.example.studyapp.model.TaskModel

class TaskListAdapter(
    val onClickDelete: (TaskEntity) -> Unit,
    val onClickEdit: (TaskEntity) -> Unit
) : ListAdapter<TaskEntity, TaskListAdapter.TaskViewHolder>(DiffCallbackTask()) {

    inner class TaskViewHolder(private var binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(taskEntity: TaskEntity) {
            binding.tvTitle.text = taskEntity.title
            binding.tvContent.text = taskEntity.content

            binding.ivEdit.setOnClickListener {
                onClickEdit(taskEntity)
            }

            binding.ivDelete.setOnClickListener {
                onClickDelete(taskEntity)

            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
       val task = getItem(position)
        holder.bindData(task)


    }
}