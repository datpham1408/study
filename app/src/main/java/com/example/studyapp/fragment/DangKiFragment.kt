package com.example.studyapp.fragment

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.studyapp.database.AppDatabase
import com.example.studyapp.database.entity.UserEntity
import com.example.studyapp.databinding.DialogDangKiBinding

class DangKiFragment : DialogFragment() {
    lateinit var binding: DialogDangKiBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogDangKiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        binding.btHuyBo.setOnClickListener {
            dismiss()
        }
        binding.btDongY.setOnClickListener {
            textUser(
                email = binding.dlEtEmail.text.toString(),
                password = binding.dlEtPassword.text.toString(),
                xacNhanPassword = binding.dlEtXacNhanPassword.text.toString()
            )
            dismiss()
        }
    }

    private fun textUser(email: String, password: String, xacNhanPassword: String) {
        when {
            email.isEmpty() -> {
                Toast.makeText(context, "enter your email id", Toast.LENGTH_LONG).show()
            }
            password.isEmpty() -> {
                Toast.makeText(context, "chua nhap password", Toast.LENGTH_SHORT).show()
            }
            password.length < 6 -> {
                Toast.makeText(context, "chua du ki tu", Toast.LENGTH_SHORT).show()
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                Toast.makeText(context, "email khong hop le", Toast.LENGTH_LONG).show()
            }
            password != xacNhanPassword -> {
                Toast.makeText(context, "password khong trung khop", Toast.LENGTH_SHORT).show()
            }
            else -> {
                saveData()
            }
        }
    }

    private fun saveData() {
        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()

        val dao = db.userDao()
        val listUser = UserEntity(
            email = binding.dlEtEmail.text.toString(),
            password = binding.dlEtPassword.text.toString(),
            xacNhanPassword = binding.dlEtXacNhanPassword.text.toString()
        )
        dao.insertAll(listUser)


    }

    override fun onStart() {
        super.onStart()


        val fullWidth = resources.displayMetrics.widthPixels

        dialog?.window?.setLayout(fullWidth, RecyclerView.LayoutParams.WRAP_CONTENT)
    }
}