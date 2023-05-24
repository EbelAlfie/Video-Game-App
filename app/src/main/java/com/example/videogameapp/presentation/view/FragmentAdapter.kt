package com.example.videogameapp.presentation.view

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.videogameapp.presentation.view.homeview.HomeFragment
import com.example.videogameapp.presentation.view.libraryview.LibraryFragment
import com.example.videogameapp.presentation.view.storeview.StoreFragment
import com.example.videogameapp.presentation.viewmodel.HomeViewModel
import com.example.videogameapp.presentation.viewmodel.StoreViewModel

class FragmentAdapter (activity: MainActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> StoreFragment()
            else -> LibraryFragment()
        }
    }
}