package com.example.studyapp.fragment

import android.R
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.studyapp.Model.MessageModel
import com.example.studyapp.adapter.diff_callback.MessageListAdapter
import com.example.studyapp.database.AppDatabase
import com.example.studyapp.database.entity.FriendEntity
import com.example.studyapp.database.entity.UserEntity
import com.example.studyapp.databinding.HomeFragmentBinding
import com.google.gson.Gson


class HomeFragment : Fragment() {
    lateinit var binding: HomeFragmentBinding
    lateinit var recyclerView: RecyclerView
    lateinit var listAdapter: MessageListAdapter
    lateinit var userEntity: UserEntity
    var listFriend = arrayListOf<FriendEntity>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentFragmentManager.setFragmentResultListener("Main", this) { _, bundle ->
            getData(bundle)

        }


        initAdapter()
        initRecyclerView()
        initListener()
        textWatcherSearch()
        getListFriendWithIdUser(getIdUser())


    }



    private fun getData(bundle: Bundle) {
        bundle.let {
            var dataArg = it.getString("Main", "")
            userEntity = Gson().fromJson(dataArg, UserEntity::class.java)
            getListFriend(userEntity)

        }

    }

    private fun initListener() {
        binding.btAdd.setOnClickListener {
            val dialog = AddFriendFragment(callback = { userName, nickName ->
                addFriend(userName, nickName)
            })
            dialog.show(parentFragmentManager, "AddFriendFragment")
        }
    }

    private fun addFriend(userName: String, nickName: String) {


        var dao = dataBase().friendDao()
        val friendEntity =
            FriendEntity(userName = userName, nickName = nickName, idUser = userEntity.id)
        dao.insertFriendAll(friendEntity)
        getListFriend(userEntity)

    }


    private fun initRecyclerView() {
        binding.rcvMessage.layoutManager = LinearLayoutManager(requireContext())

        binding.rcvMessage.adapter = listAdapter

    }

    private fun initAdapter() {
        listAdapter = MessageListAdapter(
            callback = { index ->
                var gson = Gson()
                var data = gson.toJson(listAdapter.currentList[index])
                var dialog = ViewFriendFragment(
                    callbackDelete = {


                        val dao = dataBase().friendDao()

                        dao.deleteFriend(listFriend[index])
                        getListFriend(userEntity)
                    },
                    callbackUpdate = { userName, nickName ->

                        val friends = listFriend[index]


                        friends.userName = userName
                        friends.nickName = nickName


                        val dao = dataBase().friendDao()

                        dao.updateFriend(friends = friends)

                        getListFriend(userEntity)


                    }


                )
                dialog.show(parentFragmentManager, "ViewFriendFragment")


                var bundle = Bundle()
                bundle.putString("HomeFragment", data)
                parentFragmentManager.setFragmentResult("HomeFragment", bundle)

            }
        )
    }




    fun getListFriend(userEntity: UserEntity) {
        if (listFriend.isNotEmpty()) {
            listFriend.clear()
        }


        val dao = dataBase().friendDao()
        listFriend.addAll(dao.getAllFriend(idUser = userEntity.id))
        var array = arrayListOf<MessageModel>()

        for (i in listFriend.indices) {
            var friend = listFriend[i]
            var messageModel = friend.convertToModel()
            array.add(messageModel)

        }
        listAdapter.submitList(array)


    }
    fun getListFriendWithIdUser(idUser : Int) {
        if (listFriend.isNotEmpty()) {
            listFriend.clear()
        }


        val dao = dataBase().friendDao()
        listFriend.addAll(dao.getAllFriend(idUser))
        var array = arrayListOf<MessageModel>()

        for (i in listFriend.indices) {
            var friend = listFriend[i]
            var messageModel = friend.convertToModel()
            array.add(messageModel)

        }
        listAdapter.submitList(array)


    }

    fun dataBase(): AppDatabase {
        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()
        return db
    }

    fun textWatcherSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                var search = binding.etSearch.text.toString()
                var dao = dataBase().friendDao()
                listFriend.addAll(dao.findFriendWithUserName(search))
                var array = arrayListOf<MessageModel>()
                for (i in listFriend.indices) {
                    var friend = listFriend[i]
                    var messageModel = friend.convertToModel()
                    array.add(messageModel)

                }
                listFriend.clear()
                listAdapter.submitList(array)

            }

            override fun afterTextChanged(s: Editable?) {
            }


        })
    }

    fun getIdUser():Int {
        val sharedPreferences = requireActivity().getSharedPreferences("myPref",Context.MODE_PRIVATE)
        val data = sharedPreferences.getInt("idUser",id)

        return data

    }

}