package com.example.studyapp.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.studyapp.database.AppDatabase
import com.example.studyapp.database.entity.UserEntity
import com.example.studyapp.databinding.DialogThongTinBinding
import com.google.gson.Gson

class ThongTinFragment(val callback: (String, String, String) -> Unit) : DialogFragment() {
    lateinit var binding: DialogThongTinBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogThongTinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        getUser()

        parentFragmentManager.setFragmentResultListener("CaNhanFragment",this){_,bundle->
            getData(bundle)
        }

    }

    private fun getData(bundle: Bundle) {
        bundle.let {
            val dataArg = it.getString("CaNhanFragment","")
            val data = Gson().fromJson(dataArg,UserEntity::class.java)

            binding.etThongTinName.setText(data.email)
            binding.etThongTinAge.setText(data.age)
            binding.etThongTinPhone.setText(data.phone)
        }

    }

    private fun getUser() {
         val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()
        val dao = db.userDao()
        val user =dao.findUserById(getIdUser())
        Toast.makeText(requireContext(),user.email,Toast.LENGTH_SHORT).show()
    }


    fun getIdUser():Int{
        val sharedPreferences = requireActivity().getSharedPreferences("myPref",Context.MODE_PRIVATE)

        val data = sharedPreferences.getInt("idUser",id)
        return data

    }
    private fun initListener() {
        binding.btUpdate.setOnClickListener {
            saveData()
            dismiss()
        }
    }

    private fun saveData() {
        val emails = binding.etThongTinName.text.toString()
        val age = binding.etThongTinAge.text.toString()
        val sdt = binding.etThongTinPhone.text.toString()

        callback(emails,age,sdt)
    }

    override fun onStart() {
        super.onStart()
        val fullWidth = resources.displayMetrics.widthPixels

        dialog?.window?.setLayout(fullWidth, RecyclerView.LayoutParams.WRAP_CONTENT)
    }
}