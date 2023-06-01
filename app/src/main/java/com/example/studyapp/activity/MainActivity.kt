package com.example.studyapp.activity

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.studyapp.R
import com.example.studyapp.database.AppDatabase
import com.example.studyapp.database.entity.UserEntity
import com.example.studyapp.databinding.ActivityMainBinding
import com.example.studyapp.fragment.DangKiFragment
import com.example.studyapp.fragment.HomeFragment
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initListener()
        checkRememberLogin()

    }

    private fun initListener() {
        binding.tvDangNhap.setOnClickListener {
            login(
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString()
            )
        }
        binding.tvDangKy.setOnClickListener {
            val dangKiFragment = DangKiFragment()
            dangKiFragment.show(supportFragmentManager, "DangKiFragment")
        }
    }

    private fun login(email: String, password: String) {
        when {
            email.isEmpty() -> {
                Toast.makeText(this, "nhap email", Toast.LENGTH_SHORT).show()
            }
            password.isEmpty() -> {
                Toast.makeText(this, "nhap password", Toast.LENGTH_SHORT).show()
            }
            password.length < 6 -> {
                Toast.makeText(this, "password khong du ki tu", Toast.LENGTH_SHORT).show()

            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                Toast.makeText(this, "email khong hop le", Toast.LENGTH_SHORT).show()
            }

            else -> {
                handleTextUser()

            }
        }

    }

    private fun handleTextUser() {
        var listUser: List<UserEntity>
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()

        val dao = db.userDao()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        listUser = dao.findUserWithName(email, password)

        if (listUser.isEmpty()) {
            Toast.makeText(this, "user khong ton tai", Toast.LENGTH_SHORT).show()
        } else {
            rememberSaveLogin()
            saveIdUser(listUser[0])
            var bundle = Bundle()
            var gson = Gson()
            var data = gson.toJson(listUser[0])
            bundle.putString("Main", data)
            supportFragmentManager.setFragmentResult("Main", bundle)

            supportFragmentManager.beginTransaction().apply {
                val homeFragment = HomeFragment()
                add(R.id.flLogin, homeFragment)
                commit()
            }
        }


    }

    fun rememberSaveLogin() {
        val sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("Login", true)
        editor.apply()
    }

    fun checkRememberLogin() {
        val sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val value = sharedPreferences.getBoolean("Login", false)

        if (value == true) {
            supportFragmentManager.beginTransaction().apply {
                val homeFragment = HomeFragment()
                add(R.id.flLogin, homeFragment)
                commit()

            }
        }
    }

    fun saveIdUser(idUser: UserEntity) {
        val sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val save = sharedPreferences.edit()
        save.putInt("idUser", idUser.id)
        save.apply()


    }

}