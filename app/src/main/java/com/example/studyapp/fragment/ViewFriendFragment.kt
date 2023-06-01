package com.example.studyapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.studyapp.database.entity.FriendEntity
import com.example.studyapp.databinding.DialogViewFriendBinding
import com.google.gson.Gson

class ViewFriendFragment(
    val callbackDelete: () -> Unit,
    val callbackUpdate: (String, String) -> Unit
) : DialogFragment() {
    lateinit var binding: DialogViewFriendBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogViewFriendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()

        parentFragmentManager.setFragmentResultListener("HomeFragment", this) { _, bundle ->
            getData(bundle)
        }
    }

    private fun getData(bundle: Bundle) {

        bundle.let {
            var dataArg = it.getString("HomeFragment", "")
            var listFriend = Gson().fromJson(dataArg, FriendEntity::class.java)

            binding.dlViewEtNickName.setText(listFriend.nickName)
            binding.dlViewEtUserName.setText(listFriend.userName)
        }

    }

    private fun initListener() {
        binding.btXem.setOnClickListener {

        }
        binding.btXoa.setOnClickListener {
            callbackDelete()
            dismiss()

        }
        binding.btSua.setOnClickListener {
            var userName = binding.dlViewEtUserName.text.toString()
            var nickName = binding.dlViewEtNickName.text.toString()
            callbackUpdate(userName, nickName)
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()


        val fullWidth = resources.displayMetrics.widthPixels

        dialog?.window?.setLayout(fullWidth, RecyclerView.LayoutParams.WRAP_CONTENT)
    }
}