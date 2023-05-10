package com.example.studyapp.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.studyapp.databinding.DialogViewUserBinding

class DialogViewUser : DialogFragment() {
    lateinit var binding: DialogViewUserBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogViewUserBinding.inflate(inflater, container, false)

        binding.btOut.setOnClickListener {
            dismiss()
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentFragmentManager.setFragmentResultListener("ClickView", this) { _, bundle ->
            getData(bundle)
        }
    }

    private fun getData(bundle: Bundle) {
        bundle.let {
            val id = it.getString("Id")
            val ten = it.getString("Ten")
            val tuoi = it.getString("Tuoi")
            val namSinh = it.getString("NamSinh")
            binding.tvTen.text = ten
            binding.tvTuoi.text = tuoi
            binding.tvId.text = id
            binding.tvNamSinh.text = namSinh

        }


    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

}