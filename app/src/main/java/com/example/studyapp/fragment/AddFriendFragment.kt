package com.example.studyapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.studyapp.databinding.DialogAddFriendBinding

class AddFriendFragment(val callback: (String, String) -> Unit) : DialogFragment() {
    lateinit var binding: DialogAddFriendBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogAddFriendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        binding.dlBtHuyBo.setOnClickListener {
            dismiss()
        }

        binding.dlBtDongY.setOnClickListener {
            saveData()
            dismiss()
        }

    }

    private fun saveData() {
        var userName = binding.etUserName.text.toString()
        var nickName = binding.etNickName.text.toString()

        callback(userName, nickName)

    }

    override fun onStart() {
        super.onStart()
        val fullWidth = resources.displayMetrics.widthPixels

        dialog?.window?.setLayout(fullWidth, RecyclerView.LayoutParams.WRAP_CONTENT)
    }
}