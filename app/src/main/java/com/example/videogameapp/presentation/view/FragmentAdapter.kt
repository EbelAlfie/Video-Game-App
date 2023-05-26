package com.example.videogameapp.presentation.view

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter (activity: MainActivity, private val listOfFragment: MutableList<Fragment>): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return listOfFragment.size
    }

    override fun createFragment(position: Int): Fragment {
        return listOfFragment[position]
    }
}