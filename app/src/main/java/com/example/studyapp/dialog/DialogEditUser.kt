package com.example.studyapp.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.studyapp.AppDatabase
import com.example.studyapp.database.entity.UserEntity
import com.example.studyapp.databinding.DialogEditUserBinding

class DialogEditUser : DialogFragment() {
    lateinit var binding: DialogEditUserBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogEditUserBinding.inflate(inflater, container, false)

        binding.btUpdate.setOnClickListener {
            updateData()
            dismiss()
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentFragmentManager.setFragmentResultListener("EditUser", this) { _, bundle ->
            getData(bundle)
        }

    }

    private fun getData(bundle: Bundle) {
        bundle.let {
            val ten = it.getString("Ten")
            val tuoi = it.getString("Tuoi")
            val namSinh = it.getString("NamSinh")

            binding.dlEtTen.setText(ten)
            binding.dlEtTuoi.setText(tuoi)
            binding.dlEtNamSinh.setText(namSinh)
        }

    }

    private fun updateData() {
        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()

        val dao = db.userDao()
        //create user entity
        val ten = binding.dlEtTen.text.toString()
        val tuoi = binding.dlEtTuoi.text.toString().toInt()
        val namSinh = binding.dlEtNamSinh.text.toString().toInt()
        val entity = UserEntity(ten, tuoi, namSinh)
        dao.updateUser(entity)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }
}