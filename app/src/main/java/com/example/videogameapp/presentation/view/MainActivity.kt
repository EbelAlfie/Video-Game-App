package com.example.videogameapp.presentation.view

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.example.videogameapp.RawgApp
import com.example.videogameapp.databinding.ActivityMainBinding
import com.example.videogameapp.presentation.viewmodel.HomeViewModel
import com.example.videogameapp.presentation.viewmodel.ViewModelFactory
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import javax.inject.Inject

class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var vmFactory: ViewModelFactory

    private val homeViewModel: HomeViewModel by viewModels { vmFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as RawgApp).appComponent.injectMain(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        placeFragment(HomeFragment(homeViewModel))
        setBottomNavBar()
    }

    private fun setBottomNavBar() {
        binding.apply {
            fragmentViewPager.adapter = FragmentAdapter(this@MainActivity, homeViewModel)
                viewNavbar.setOnItemSelectedListener{
                    fragmentViewPager.currentItem = when (it.itemId) {
                        viewNavbar[0].id -> 0
                        else -> 0
                    }
                    true
                }
        }
    }

    private fun placeFragment(homeFragment: HomeFragment) {

    }
}