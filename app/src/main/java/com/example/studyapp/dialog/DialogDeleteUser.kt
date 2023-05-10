package com.example.studyapp.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.room.Room
import com.example.studyapp.AppDatabase
import com.example.studyapp.database.entity.UserEntity
import com.example.studyapp.databinding.DialogDeleteUserBinding

class DialogDeleteUser : DialogFragment() {
    lateinit var binding: DialogDeleteUserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogDeleteUserBinding.inflate(inflater, container, false)

        binding.btNo.setOnClickListener {
            dismiss()
        }
        binding.btYes.setOnClickListener {

            dismiss()
        }
        return binding.root
    }

    private fun deleteUser(userEntity: UserEntity) {
        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()

        val dao = db.userDao()
        dao.delete(userEntity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        dialog?.window?.setBackgroundDrawable(android.graphics.Color.TRANSPARENT)

    }
}