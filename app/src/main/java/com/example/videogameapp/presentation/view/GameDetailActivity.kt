package com.example.videogameapp.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.videogameapp.R
import com.example.videogameapp.Utils
import com.example.videogameapp.databinding.ActivityGameDetailBinding
import com.example.videogameapp.domain.entity.GameDetailedEntity
import com.example.videogameapp.presentation.viewmodel.HomeViewModel
import com.example.videogameapp.presentation.viewmodel.ViewModelFactory
import com.squareup.picasso.Picasso
import javax.inject.Inject

class GameDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameDetailBinding

    @Inject
    lateinit var vmFactory: ViewModelFactory

    private val homeViewModel: HomeViewModel by viewModels {
        vmFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getIntExtra(Utils.ID_KEY, -1)
        if (id == -1) return
        reqItem(id)
    }

    private fun reqItem(id: Int) {
        homeViewModel.getGameDetail(id)
        homeViewModel.getDetailedGameData().observe(this){
            if (it == null) return@observe
            setView(it)
        }
    }

    private fun setView(data: GameDetailedEntity) {
        binding.apply {
            Picasso.get().load(data.backgroundImageAdditional).into(ivGameImage)
            tvGameTitle.text = data.name
        }
    }
}