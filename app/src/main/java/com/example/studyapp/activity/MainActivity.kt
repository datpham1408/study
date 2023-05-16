package com.example.studyapp.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.studyapp.R
import com.example.studyapp.adapter.diff_callback.TaskListAdapter
import com.example.studyapp.database.AppDatabase
import com.example.studyapp.database.entity.TaskEntity
import com.example.studyapp.databinding.ActivityMainBinding
import com.example.studyapp.dialog.DialogDeleteTask
import com.example.studyapp.dialog.DialogEditTask
import com.example.studyapp.dialog.TaskDialogFragment
import com.example.studyapp.fragment.SearchFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var listAdapter: TaskListAdapter

//    var modelEntity = TaskEntity("","")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAdapter()
        initRecyclerView()
        supportFragmentManager.setFragmentResultListener("TaskDialog", this) { _, bundle ->
            loadData()
        }
        loadData()



        initListener()

    }

    private fun loadData() {
        loadDatabase()
    }

    private fun loadDatabase() {
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()

        val dao = db.taskDao()
        val tasks = dao.getAll()

        listAdapter.submitList(tasks)

    }

    private fun initListener() {
        binding.btAdd.setOnClickListener {
            var dialog = TaskDialogFragment()
            dialog.show(supportFragmentManager, dialog::class.java.simpleName)
        }

        binding.btSearch.setOnClickListener {
           addFragment()

        }
    }

    private fun addFragment() {
        supportFragmentManager.beginTransaction().apply {
            var searchFragment = SearchFragment()
            add(R.id.flSearch,searchFragment)
            addToBackStack(searchFragment::class.java.simpleName)
            commit()

        }
    }

    private fun initRecyclerView() {
        binding.flRcvTask.layoutManager = LinearLayoutManager(this)

        binding.flRcvTask.adapter = listAdapter
    }

    private fun initAdapter() {

        listAdapter = TaskListAdapter(
            onClickEdit = {
                handleClickEdit(it)
            },
            onClickDelete = {
//                modelEntity = it
                handleClickDelete(it)
            }


        )

    }

    private fun handleClickDelete(taskEntity: TaskEntity) {

        val bundle = Bundle()
        supportFragmentManager.setFragmentResult("DeleteTask", bundle)
        var dialog = DialogDeleteTask {
            deleteData(taskEntity)
        }
        dialog.show(supportFragmentManager, "DialogDelete")

    }

    private fun handleClickEdit(taskEntity: TaskEntity) {

        val bundle = Bundle()
        bundle.putString("title", taskEntity.title)
        bundle.putString("content", taskEntity.content)
//        bundle.putSerializable("Entity",taskEntity)
        supportFragmentManager.setFragmentResult("EditTask", bundle)
        var dialog = DialogEditTask{
            it.id = taskEntity.id
            updateData(it)
        }
        dialog.show(supportFragmentManager, "DialogEdit")
    }

    private fun updateData(taskEntity: TaskEntity) {
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()

        val dao = db.taskDao()

        dao.updateUser(taskEntity)

        loadData()
    }

    private fun deleteData(taskEntity: TaskEntity) {
        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()

        val dao = db.taskDao()
        dao.delete(taskEntity)
        loadData()


    }


}