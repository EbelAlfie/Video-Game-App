package com.example.videogameapp.presentation.view

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.videogameapp.presentation.viewmodel.HomeViewModel

class FragmentAdapter (activity: MainActivity, private val viewModel: HomeViewModel): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 1
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment(viewModel)
            else -> HomeFragment(viewModel)
        }
    }
}