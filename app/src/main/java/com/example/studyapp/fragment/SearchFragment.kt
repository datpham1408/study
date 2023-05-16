package com.example.studyapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.studyapp.adapter.diff_callback.TaskListAdapter
import com.example.studyapp.database.AppDatabase
import com.example.studyapp.database.entity.TaskEntity
import com.example.studyapp.databinding.SearchFragmentBinding
import com.example.studyapp.dialog.DialogDeleteTask
import com.example.studyapp.dialog.DialogEditTask

class SearchFragment : Fragment() {
    lateinit var binding: SearchFragmentBinding
    lateinit var listAdapter: TaskListAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initRecyclerView()
        initListener()
    }

    private fun initRecyclerView() {
        binding.rcvSearch.layoutManager = LinearLayoutManager(requireContext())

        binding.rcvSearch.adapter = listAdapter
    }

    private fun loadData() {
        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()

        val dao = db.taskDao()
        val tasks = dao.getAll()

        listAdapter.submitList(tasks)

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
        parentFragmentManager.setFragmentResult("DeleteTask", bundle)
        var dialog = DialogDeleteTask {
            deleteData(taskEntity)
        }
        dialog.show(parentFragmentManager, "DialogDelete")

    }

    private fun handleClickEdit(taskEntity: TaskEntity) {

        val bundle = Bundle()
        bundle.putString("title", taskEntity.title)
        bundle.putString("content", taskEntity.content)
//        bundle.putSerializable("Entity",taskEntity)
        parentFragmentManager.setFragmentResult("EditTask", bundle)
        var dialog = DialogEditTask {
            it.id = taskEntity.id
            updateData(it)
        }
        dialog.show(parentFragmentManager, "DialogEdit")
    }

    private fun updateData(taskEntity: TaskEntity) {
        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()

        val dao = db.taskDao()

        dao.updateUser(taskEntity)

        loadData()
    }

    private fun deleteData(taskEntity: TaskEntity) {
        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()

        val dao = db.taskDao()
        dao.delete(taskEntity)
        loadData()


    }


    private fun initListener() {
        binding.searchFrgBtSearch.setOnClickListener {
            handleSearch()
        }
    }

    private fun handleSearch() {
        val keyWork = binding.searchFrgEtTitle.text.toString()

        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()

        val dao = db.taskDao()
        var search = dao.findTaskWithName(keyWork)

        listAdapter.submitList(search)
    }
}

