package com.example.studyapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        demoDatabase()
    }

    private fun demoDatabase() {
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()

        val userDao = db.userDao()

        userDao.insertAll(UserEntity(id  = 2 ,email = "2@gmail.com", password = "2222"))

        val listUser = userDao.getAll()

        var email = ""
        listUser.forEach {
            email += "${it.email}\n"
        }

//        Log.d("tempDb", listUser.size.toString())
        Toast.makeText(this, email, Toast.LENGTH_SHORT).show()
    }
}