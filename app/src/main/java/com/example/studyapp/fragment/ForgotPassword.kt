package com.example.studyapp.fragment

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.room.Room
import com.example.studyapp.database.AppDatabase
import com.example.studyapp.database.entity.UserEntity
import com.example.studyapp.databinding.DialogForgotPasswordBinding

class ForgotPassword : DialogFragment() {
    lateinit var binding: DialogForgotPasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    private fun initListener() {
        binding.dlForgotPasswordHuyBo.setOnClickListener {
            dismiss()
        }
        binding.dlForgotPasswordXacNhan.setOnClickListener {
            textUser()
            saveUser(
                email = binding.dlForgotPasswordEmail.text.toString(),
                password = binding.dlForgotPasswordMatKhauMoi.text.toString()
            )
            dismiss()
        }
    }

    private fun textUser() {
        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()

        val dao = db.userDao()

        val email = binding.dlForgotPasswordEmail.text.toString()

        dao.forgotPassword(email)

    }

    private fun saveUser(email: String, password: String) {
        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()

        val dao = db.userDao()
        val user = UserEntity(email, password)

        dao.insertAll(user)


    }
}