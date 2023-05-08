package com.example.studyapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.studyapp.AppDatabase
import com.example.studyapp.adapter.SubjectListAdapter
import com.example.studyapp.database.entity.UserEntity
import com.example.studyapp.databinding.SubjectFragmentBinding

class SubjectFragment : Fragment() {
    lateinit var listAdapter: SubjectListAdapter
    lateinit var binding: SubjectFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SubjectFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initRecyclerView()
        parentFragmentManager.setFragmentResultListener("Main", this) { requestKey, bundle ->
            loadData()
        }
        loadData()
    }

    private fun loadData() {
        loadDatabase()
    }

    private fun loadDatabase() {
        var db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()

        var dao = db.userDao()
        var users = dao.getAll()

        listAdapter.submitList(users)

    }

    private fun initRecyclerView() {
        binding.rcvSubject.layoutManager = LinearLayoutManager(requireContext())

        binding.rcvSubject.adapter = listAdapter
    }

    private fun initAdapter() {

        listAdapter = SubjectListAdapter(
            onClickView = {
                handleClickView(it)
            }, onClickEdit = {
                handleClickEdit(it)
            }, onClickDelete = {
                handleClickDelete(it)
            }
        )


    }

    private fun handleClickDelete(userEntity: UserEntity) {
        /*
        * show dialog fragment
        * voi UI la file dialog_delete_user.xml
        * */
        Toast.makeText(context, "handleClickDelete", Toast.LENGTH_SHORT).show()
    }

    private fun handleClickEdit(userEntity: UserEntity) {
        /*
        * show dialog fragment user entity click
        * voi UI la file dialog_edit_user.xml
        *
        * step by step
        * 1. show dialog fragment
        * 2. goi setResult fragment
        * */
        Toast.makeText(context, "handleClickEdit", Toast.LENGTH_SHORT).show()
    }

    private fun handleClickView(userEntity: UserEntity) {
        /*show dialog fragment thong tin user
        * voi UI la file dialog_view_user.xml
        */
        Toast.makeText(context, "handleClickView", Toast.LENGTH_SHORT).show()
    }

}