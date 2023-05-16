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
import com.example.studyapp.databinding.TaskDialogFragmentBinding

class TaskDialogFragment : DialogFragment() {

    lateinit var binding: TaskDialogFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = TaskDialogFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    private fun initListener() {
        binding.tvAdd.setOnClickListener {

            saveData()

            var bundle = Bundle()
            parentFragmentManager.setFragmentResult("TaskDialog",bundle)

            dismiss()

        }
        binding.tvCancel.setOnClickListener {
            dismiss()

        }
    }

    private fun saveData() {

        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()

        var dao = db.taskDao()
        var title = binding.etTitle.text.toString()
        var content = binding.etContent.text.toString()
        var taskEntity = TaskEntity(title,content)
        dao.insertAll(taskEntity)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }
}