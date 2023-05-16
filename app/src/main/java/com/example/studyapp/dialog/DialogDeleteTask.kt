package com.example.studyapp.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.room.Room
import com.example.studyapp.database.AppDatabase
import com.example.studyapp.database.entity.TaskEntity
import com.example.studyapp.databinding.DeleteDialogFragmentBinding

class DialogDeleteTask(val callback: () -> Unit) : DialogFragment() {

    lateinit var binding: DeleteDialogFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DeleteDialogFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentFragmentManager.setFragmentResultListener("DeleteTask", this) { _, bundle ->
        }

        initListener()
    }

    private fun initListener() {
        binding.btNo.setOnClickListener {
            dismiss()
        }
        binding.btYes.setOnClickListener {
            callback()
            dismiss()
        }
    }

}