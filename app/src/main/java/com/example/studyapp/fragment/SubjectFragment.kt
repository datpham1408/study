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
import com.example.studyapp.dialog.DialogDeleteUser
import com.example.studyapp.dialog.DialogEditUser
import com.example.studyapp.dialog.DialogViewUser

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
        var dialog = DialogDeleteUser()
        dialog.show(parentFragmentManager,"DialogDelete")


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

        val bundle = Bundle()
        bundle.putString("Ten", userEntity.ten)
        bundle.putString("Tuoi", userEntity.tuoi.toString())
        bundle.putString("NamSinh", userEntity.namSinh.toString())
        parentFragmentManager.setFragmentResult("EditUser", bundle)



        var dialog = DialogEditUser()
        dialog.show(parentFragmentManager,"DialogEdit")
    }

    private fun handleClickView(userEntity: UserEntity) {
        /*show dialog fragment thong tin user
        * voi UI la file dialog_view_user.xml
        */

        val bundle = Bundle()
        bundle.putString("Ten", userEntity.ten)
        bundle.putString("Id", userEntity.id.toString())
        bundle.putString("Tuoi", userEntity.tuoi.toString())
        bundle.putString("NamSinh", userEntity.namSinh.toString())
        parentFragmentManager.setFragmentResult("ClickView", bundle)

        var dialog = DialogViewUser()
        dialog.show(parentFragmentManager,"DialogView")
    }

}