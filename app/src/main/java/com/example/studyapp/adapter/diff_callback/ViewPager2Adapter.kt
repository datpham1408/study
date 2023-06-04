package com.example.studyapp.adapter.diff_callback

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.studyapp.fragment.*

class MyPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private val fragments = listOf(
        HomeFragment(),
        DanhBaFragment(),
        KhamPhaFragment(),
        NhatKyFragment(),
        CaNhanFragment(),
    )

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]
}