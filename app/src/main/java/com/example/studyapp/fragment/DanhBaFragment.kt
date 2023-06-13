package com.example.studyapp.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.studyapp.Model.UserDanhBa
import com.example.studyapp.adapter.diff_callback.DanhBaListAdapter
import com.example.studyapp.database.AppDatabase
import com.example.studyapp.databinding.DanhBaFragmentBinding

class DanhBaFragment : Fragment() {
    lateinit var binding: DanhBaFragmentBinding
    lateinit var recyclerView: RecyclerView
    lateinit var listAdapter: DanhBaListAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DanhBaFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initRecyclerView()
        getDataBase()

    }

    private fun getDataBase() {

        val listUserDanhBa = arrayListOf<UserDanhBa>()
        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()
        val dao = db.friendDao()

        val data = dao.getAllFriend(getUserId())
        for (i in data.indices) {

            var friend = data[i].convertToUserDanhBaModel()

            listUserDanhBa.add(friend)


        }
        listAdapter.submitList(listUserDanhBa)

    }

    private fun initRecyclerView() {
        binding.rcvDanhBa.layoutManager = LinearLayoutManager(requireContext())

        binding.rcvDanhBa.adapter = listAdapter

    }

    private fun initAdapter() {

        listAdapter = DanhBaListAdapter()

    }

    fun getUserId(): Int {
        val sharedPreferences =
            requireActivity().getSharedPreferences("myPref", Context.MODE_PRIVATE)

        val data = sharedPreferences.getInt("idUser", id)

        return data

    }
}