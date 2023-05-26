package com.example.videogameapp.presentation.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.videogameapp.R
import com.example.videogameapp.RawgApp
import com.example.videogameapp.databinding.ActivityMainBinding
import com.example.videogameapp.databinding.CustomMainToolbarBinding
import com.example.videogameapp.presentation.view.homeview.HomeFragment
import com.example.videogameapp.presentation.view.libraryview.LibraryFragment
import com.example.videogameapp.presentation.view.storeview.StoreFragment
import com.example.videogameapp.presentation.viewmodel.*
import javax.inject.Inject

class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var vmFactory: ViewModelFactory

    private val homeViewModel: HomeViewModel by viewModels { vmFactory }

    private val storeViewModel: StoreViewModel by viewModels { vmFactory }

    private val libraryViewModel: LibraryViewModel by viewModels { vmFactory }

    private val mainViewModel: MainViewModel by viewModels()

    private val listOfFragments: MutableList<Fragment> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as RawgApp).appComponent.injectMain(this)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                mainViewModel.isLoading.value
            }
        }
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFragments()
        setBottomNavBar()
    }

    private fun initFragments() {
        listOfFragments.add(HomeFragment())
        listOfFragments.add(StoreFragment())
        listOfFragments.add(LibraryFragment())
    }

    private fun setBottomNavBar() {
        binding.apply {
            fragmentViewPager.adapter = FragmentAdapter(this@MainActivity, listOfFragments)

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

    fun fetchStoreViewModel(): StoreViewModel {
        return storeViewModel
    }

    fun fetchHomeViewModel(): HomeViewModel {
        return homeViewModel
    }

    fun fetchLibraryViewModel(): LibraryViewModel {
        return libraryViewModel
    }

    fun getToolbar(): CustomMainToolbarBinding {
        return binding.toolbar
    }
}