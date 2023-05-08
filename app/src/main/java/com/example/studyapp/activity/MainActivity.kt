package com.example.studyapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.studyapp.AppDatabase
import com.example.studyapp.R
import com.example.studyapp.fragment.SubjectFragment
import com.example.studyapp.database.entity.UserEntity
import com.example.studyapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var ten: String? = null
    var tuoi = 0
    var namSinh = 0
    var result: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
//        getData()
        addFragment()
//        roomDatabase()
    }

//    val db = Room.databaseBuilder(
//        applicationContext,
//        AppDatabase::class.java, "database-name"
//    ).allowMainThreadQueries().build()
//
//    val userDao = db.userDao()
//    userDao.getAll()
//}private fun roomDatabase() {


//    private fun getData() {
//        ten = binding.etTen.text.toString()
//        tuoi = binding.etTuoi.text.toString().toInt()
//        namSinh = binding.etNamSinh.text.toString().toInt()
//
//        result = "$ten-$tuoi-$namSinh"
//    }

    private fun initListener() {
        binding.btSubmit.setOnClickListener {
            var bundle =Bundle()
//            bundle.putString("Main",result)
//            supportFragmentManager.setFragmentResult("Main",bundle)
//
//            addFragment()
            saveData()
            supportFragmentManager.setFragmentResult("Main",bundle)
        }
    }

    private fun saveData() {
        /*
        * 1 - Tao room database
        * 2 - dung room save user entiry xuong database
        * */

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()

        var dao = db.userDao()
        //create user entity
        var ten = binding.etTen.text.toString()
        var tuoi = binding.etTuoi.text.toString().toInt()
        var namSinh = binding.etNamSinh.text.toString().toInt()
        val entity = UserEntity(ten, tuoi, namSinh)
        dao.insertAll(entity)
    }

    private fun addFragment() {
        supportFragmentManager.beginTransaction().apply {
            var subjectFragment = SubjectFragment()
            add(R.id.act_flRCV, subjectFragment)
            commit()
        }
    }


}