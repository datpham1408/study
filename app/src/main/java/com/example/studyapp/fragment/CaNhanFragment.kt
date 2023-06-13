package com.example.studyapp.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.studyapp.activity.LoginActivity
import com.example.studyapp.database.AppDatabase
import com.example.studyapp.database.entity.UserEntity
import com.example.studyapp.databinding.CaNhanFragmentBinding
import com.google.gson.Gson

class CaNhanFragment : Fragment() {
    lateinit var binding: CaNhanFragmentBinding
    lateinit var userEntity: UserEntity
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CaNhanFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        getUser()
        initData()


    }


    private fun getUser() {
        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()
        val dao = db.userDao()
        userEntity = dao.findUserById(getIdUser())
        Toast.makeText(requireContext(), userEntity.email, Toast.LENGTH_SHORT).show()
    }


    fun getIdUser(): Int {
        val sharedPreferences =
            requireActivity().getSharedPreferences("myPref", Context.MODE_PRIVATE)

        val data = sharedPreferences.getInt("idUser", id)
        return data

    }

    private fun initData() {
        binding.ivAvatarCaNhan.text = userEntity.email?.first()?.uppercase()
    }

    private fun initListener() {
        binding.llThongTinCaNhan.setOnClickListener {
            val dialog = ThongTinFragment(
                callback = { email, age, sdt ->

                    userEntity.email = email
                    userEntity.age = age
                    userEntity.phone = sdt

                    val db = Room.databaseBuilder(
                        requireContext(),
                        AppDatabase::class.java, "database-name"
                    ).allowMainThreadQueries().build()
                    val dao = db.userDao()
//                    val thongTin = UserEntity(email, age, sdt)

                    dao.updateUser(userEntity)


                }
            )
            val bundle = Bundle()
            val gson = Gson()
            val data = gson.toJson(userEntity)
            bundle.putString("CaNhanFragment", data)
            parentFragmentManager.setFragmentResult("CaNhanFragment", bundle)
            dialog.show(parentFragmentManager, "ThongTinFragment")
        }
        binding.btDangXuat.setOnClickListener {
            logOut()
            val intent = Intent(requireActivity(),LoginActivity::class.java)

            startActivity(intent)


        }


    }

    private fun logOut() {
        val sharedPreferences =
            requireActivity().getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val edit = sharedPreferences.edit()
        edit.putBoolean("Login",false)
        edit.apply()
    }
}