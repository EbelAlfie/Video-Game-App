package com.example.videogameapp.presentation.view.homeview

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.videogameapp.R
import com.example.videogameapp.RawgApp
import com.example.videogameapp.Utils
import com.example.videogameapp.Utils.fromHtml
import com.example.videogameapp.databinding.ActivityGameDetailBinding
import com.example.videogameapp.domain.entity.gameentity.GameDetailedEntity
import com.example.videogameapp.domain.entity.gameentity.GameItemEntity
import com.example.videogameapp.domain.entity.gameentity.ScreenShotEntity
import com.example.videogameapp.presentation.viewmodel.HomeViewModel
import com.example.videogameapp.presentation.viewmodel.ViewModelFactory
import com.squareup.picasso.Picasso
import java.util.*
import javax.inject.Inject
import kotlin.math.abs

class GameDetailActivity : AppCompatActivity(), GameStoreLinkAdapter.SetOnItemClickListener {
    private lateinit var binding: ActivityGameDetailBinding
    private lateinit var gameStoreLinkAdapter: GameStoreLinkAdapter
    private lateinit var screenShotsAdapter: GameScreenShotAdapter
    private var game: GameItemEntity? = null

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

        setStoreLinkRv()
        setScreenShotSlider()
        setObserver()
        game = homeViewModel.getIntent(intent)
        if (game == null) return
        reqItem(game!!.id)
    }

    private fun setStoreObserver(gameData: GameDetailedEntity) {
        homeViewModel.getStoreLiveData().observe(this) {storeEntity ->
            if (storeEntity.isEmpty() || gameData.store.isEmpty()) return@observe
            storeEntity.forEachIndexed { index, item ->
                item.name = gameData.store[index].name
            }
            gameStoreLinkAdapter.addData(storeEntity.toMutableList())
        }
    }

    private fun setObserver() {
        homeViewModel.getDetailedGameData().observe(this){gameData ->
            if (gameData == null) return@observe
            getStoreLink(gameData.id)
            gameData.poster = game!!.backgroundImage
            gameData.screenShots = game!!.screenShots
            setStoreObserver(gameData)
            setView(gameData)
        }
    }

    private fun getStoreLink(id: Long) {
        homeViewModel.getGameStoreLink(id)
    }

    private fun setStoreLinkRv() {
        binding.apply{
            rvListStore.layoutManager = LinearLayoutManager(this@GameDetailActivity, LinearLayoutManager.HORIZONTAL, false)
            gameStoreLinkAdapter = GameStoreLinkAdapter(mutableListOf(), this@GameDetailActivity)
            rvListStore.adapter = gameStoreLinkAdapter
        }
    }

    private fun reqItem(id: Long) {
        homeViewModel.getGameDetail(id)
    }

    private fun setView(data: GameDetailedEntity) {
        binding.apply {
            setImagePoster(ivGameImage, data)
            setMetacritics(tvMetacritic, data)

            screenShotsAdapter.updateList(data.screenShots as List<ScreenShotEntity>)

            tvGameReleasedDate.text = data.getReleasedDate()
            tvGameTitle.text = data.name
            tvGameDesc.text = data.desc.fromHtml()
            tvGameAgeRating.text = data.ageRating
            tvGameGenre.text = data.genres.joinToString { it.genreName }
            tvGamePlatform.text = data.platforms.joinToString { it.platform }
            tvGameDeveloper.text = data.developer.joinToString { it.name }
            tvGamePublisher.text = data.publishers.joinToString { it.publisher }
            tvGameTags.text = data.tags.joinToString { it.tagName }

        }
    }

    private fun setTransformation(vpImageSlider: ViewPager2) {
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((0 * Resources.getSystem().displayMetrics.density).toInt()))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = (0.80f + r * 0.20f)
        }
        vpImageSlider.setPageTransformer(compositePageTransformer)
    }

    private fun setScreenShotSlider() {
        binding.apply {
            screenShotsAdapter = GameScreenShotAdapter(game?.screenShots?.toMutableList() ?: mutableListOf())
            vpImageSlider.apply{
                clipToPadding = false
                clipChildren = false
                offscreenPageLimit = 5
                (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                setTransformation(this)
                adapter = screenShotsAdapter
            }
        }
        setAutoSlide()
    }

    private fun setAutoSlide() {
        val handler = Handler(Looper.getMainLooper())
        val timer = Timer()
        var currentItem = 0
        val update = Runnable {
            binding.vpImageSlider.setCurrentItem(currentItem, true)
            if (currentItem == game?.screenShots?.size) currentItem = 0
            else currentItem++
        }
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, Utils.DELAY_TIME, Utils.PERIODE_TIME)
    }


    private fun setImagePoster(ivGameImage: ImageView, data: GameDetailedEntity) {
        if (data.poster != null && data.poster!!.isNotBlank()) {
            Picasso.get().load(data.poster).into(ivGameImage)
        }else ivGameImage.setImageResource(R.drawable.baseline_broken_image_24)
    }

    private fun setMetacritics(tvMetacritic: TextView, data: GameDetailedEntity) {
        val metaCritic = data.getMetacritics()
        if (metaCritic.isBlank()) return
        tvMetacritic.visibility = View.VISIBLE
        tvMetacritic.text = getString(R.string.metacritic, data.metaCritic)
        tvMetacritic.setTextColor(getColor(data.getMetacriticColor()))
    }

    override fun onItemClicked(position: Int) {

    }
}