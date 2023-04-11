package com.example.videogameapp.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.videogameapp.R
import com.example.videogameapp.RawgApp
import com.example.videogameapp.Utils
import com.example.videogameapp.Utils.htmlFormater
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
        (application as RawgApp).appComponent.injectDetailed(this)
        super.onCreate(savedInstanceState)
        binding = ActivityGameDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getLongExtra(Utils.ID_KEY, -1L)
        if (id == -1L) return
        reqItem(id)
    }

    private fun reqItem(id: Long) {
        homeViewModel.getGameDetail(id)
        homeViewModel.getDetailedGameData().observe(this){
            if (it == null) return@observe
            setView(it)
        }
    }

    private fun setView(data: GameDetailedEntity) {
        binding.apply {
            if (data.backgroundImageAdditional.isNotBlank()) {
                Picasso.get().load(data.backgroundImageAdditional).into(ivGameImage)
            }else ivGameImage.setImageResource(R.drawable.baseline_broken_image_24)
            tvGameTitle.text = data.name
            tvGameDesc.text = data.desc.htmlFormater()
            tvGameReleasedDate.text = data.dateReleased
            tvGameGenre.text = data.genres.joinToString { it.genreName }
            tvGamePlatform.text = data.platforms.joinToString { it.platform }
            tvGameDeveloper.text = data.developer.joinToString { it.name }
            tvGameAgeRating.text = data.ageRating

        }
    }
}