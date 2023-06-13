package com.example.studyapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.createBitmap
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studyapp.Model.MiniAppModel
import com.example.studyapp.R
import com.example.studyapp.adapter.diff_callback.MiniAppListAdapter
import com.example.studyapp.databinding.KhamPhaFragmentBinding

class KhamPhaFragment : Fragment() {
    lateinit var binding: KhamPhaFragmentBinding
    lateinit var recyclerView: RecyclerView
    lateinit var listAdapter: MiniAppListAdapter
    val images = intArrayOf(
        R.drawable.shop,
        R.drawable.pulic,
        R.drawable.smile,
        R.drawable.apps,
        R.drawable.zalo,
        R.drawable.telephone,
        R.drawable.invoice,
        R.drawable.moneygrowth,
        R.drawable.menu


    )
    val titles =
        arrayListOf(
            "Shop",
            "Dịch vụ công",
            "Sticker",
            "Fiza",
            "Ví ZaloPay",
            "Nap tiền ĐT",
            "Trả Hoá Đơn",
            "Tích luỹ",
            "Xem thêm"
        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = KhamPhaFragmentBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initRecyclerView()
        initListModel()
    }

    private fun initListModel() {
        val miniApp = arrayListOf<MiniAppModel>()

        for (i in images.indices){
            val image =images[i]
            val title = titles[i]

            val item = MiniAppModel(image, title)

            miniApp.add(item)
        }

        listAdapter.submitList(miniApp)

    }

    private fun initRecyclerView() {
        binding.rcvMiniApps.layoutManager = GridLayoutManager(requireContext(), 4)

        binding.rcvMiniApps.adapter = listAdapter

    }

    private fun initAdapter() {
        listAdapter = MiniAppListAdapter()

    }
}