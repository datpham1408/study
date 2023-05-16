package com.example.studyapp.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.studyapp.database.AppDatabase
import com.example.studyapp.database.entity.TaskEntity
import com.example.studyapp.databinding.EditDialogFragmentBinding

class DialogEditTask(val callback : (TaskEntity)->Unit) :DialogFragment(){

    lateinit var binding: EditDialogFragmentBinding
    var taskEntity = TaskEntity("","")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EditDialogFragmentBinding.inflate(inflater,container,false)
        binding.tvUpdate.setOnClickListener {
            saveData()
            dismiss()
        }
        return binding.root
    }

    private fun saveData() {

        taskEntity.title = binding.etTitle.text.toString()
        taskEntity.content = binding.etContent.text.toString()

        callback(taskEntity)



    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentFragmentManager.setFragmentResultListener("EditTask",this){_,bundle->
            getData(bundle)

        }


    }

    private fun getData(bundle: Bundle) {

        bundle.let {
//            taskEntity = bundle.getSerializable("Entity",TaskEntity("",""))!!
            val title = it.getString("title","")
            val content = it.getString("content","")

            binding.etTitle.setText(title)
            binding.etContent.setText(content)

            taskEntity.title = title
            taskEntity.content = content
        }

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }
}