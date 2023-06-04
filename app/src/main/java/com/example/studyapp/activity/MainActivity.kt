@file:Suppress("DEPRECATION")

package com.example.studyapp.activity

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.FragmentPagerAdapter
import androidx.room.Room
import com.example.studyapp.R
import com.example.studyapp.adapter.diff_callback.MyPagerAdapter

import com.example.studyapp.database.AppDatabase
import com.example.studyapp.database.entity.UserEntity
import com.example.studyapp.databinding.ActivityMainBinding
import com.example.studyapp.fragment.*
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()


    }

    private fun initListener() {


        binding.viewPager2.adapter = MyPagerAdapter(supportFragmentManager, lifecycle)


        val tabLayoutMediator =
            TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
                val customTabItem = LayoutInflater.from(this)
                    .inflate(R.layout.item_tab, null) as LinearLayoutCompat

                val textView = customTabItem.findViewById<TextView>(R.id.itemTab_tvTitle)
                val imageView = customTabItem.findViewById<ImageView>(R.id.itemTab_ivTab)

                val textViews = arrayListOf(
                    R.string.tab1_title,
                    R.string.tab2_title,
                    R.string.tab3_title,
                    R.string.tab4_title,
                    R.string.tab5_title
                )
                val imageViews = intArrayOf(
                    R.drawable.messenger,
                    R.drawable.ic_danhba,
                    R.drawable.ic_khampha,
                    R.drawable.ic_nhatky,
                    R.drawable.ic_canhan
                )


                textView.text = getStrings(textViews[position])
                imageView.setImageResource(imageViews[position])
                tab.customView = customTabItem


            }

        tabLayoutMediator.attach()

    }


    fun getStrings(id: Int): String {
        return this.resources.getString(id)
    }


}