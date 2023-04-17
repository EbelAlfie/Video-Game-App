package com.example.videogameapp.presentation.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.videogameapp.R
import com.example.videogameapp.RawgApp
import com.example.videogameapp.databinding.ActivityMainBinding
import com.example.videogameapp.presentation.viewmodel.HomeViewModel
import com.example.videogameapp.presentation.viewmodel.StoreViewModel
import com.example.videogameapp.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var vmFactory: ViewModelFactory

    private val homeViewModel: HomeViewModel by viewModels { vmFactory }

    private val storeViewModel: StoreViewModel by viewModels { vmFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as RawgApp).appComponent.injectMain(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBottomNavBar()
    }

    private fun setBottomNavBar() {
        binding.apply {
            fragmentViewPager.adapter = FragmentAdapter(this@MainActivity, homeViewModel, storeViewModel)

            viewNavbar.setOnItemSelectedListener{
                fragmentViewPager.currentItem = when (it.itemId) {
                    R.id.btn_nav_home -> 0
                    R.id.btn_nav_store -> 1
                    R.id.btn_nav_library -> 2
                    else -> 0
                }
                true
            }

            fragmentViewPager.registerOnPageChangeCallback(object: OnPageChangeCallback() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) { viewNavbar.menu.getItem(position).isChecked = true }
            })
        }
    }
}