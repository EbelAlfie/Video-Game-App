package com.example.videogameapp.presentation.view.homeview

import android.app.AlertDialog
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.lifecycleScope
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
import com.example.videogameapp.presentation.viewmodel.GameDetailViewModel
import com.example.videogameapp.presentation.viewmodel.ViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

class GameDetailActivity : AppCompatActivity(), GameStoreLinkAdapter.SetOnItemClickListener, GameScreenShotAdapter.SetOnImageClickListener {
    private lateinit var binding: ActivityGameDetailBinding
    private lateinit var gameStoreLinkAdapter: GameStoreLinkAdapter
    private lateinit var screenShotsAdapter: GameScreenShotAdapter
    private lateinit var dlcAdapter: DlcPagingAdapter
    private lateinit var loadingDialog: AlertDialog

    @Inject
    lateinit var vmFactory: ViewModelFactory

    private val gameDetailViewModel: GameDetailViewModel by viewModels { vmFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as RawgApp).appComponent.injectDetailed(this)
        super.onCreate(savedInstanceState)
        binding = ActivityGameDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gameDetailViewModel.getId(intent)
        if (gameDetailViewModel.gameId == -1L) return
        setRecyclerViews()
        setObserver()
        setToolbar()
        gameDetailViewModel.checkNetworkState(this, gameDetailViewModel.gameId, fun(id) { reqItem(id) })
    }

    private fun setToolbar() {
        binding.toolbar.btnBack.setOnClickListener {
            finish()
        }
        binding.toolbar.btnRefresh.setOnClickListener {
            gameDetailViewModel.checkNetworkState(this, gameDetailViewModel.gameId, fun(id) { reqItem(id) })
        }
    }

    private fun setRecyclerViews() {
        setStoreLinkRv()
        setDlcRv()
        setScreenShotSlider()
    }

    private fun setStoreLinkRv() {
        binding.apply{
            rvListStore.layoutManager = LinearLayoutManager(this@GameDetailActivity, LinearLayoutManager.HORIZONTAL, false)
            gameStoreLinkAdapter = GameStoreLinkAdapter(mutableListOf(), this@GameDetailActivity)
            rvListStore.adapter = gameStoreLinkAdapter
        }
        loadingDialog = Utils.createLoading(this).create()
    }

    private fun setDlcRv() {
        binding.apply {
            rvListDlc.layoutManager = LinearLayoutManager(this@GameDetailActivity)
            dlcAdapter = DlcPagingAdapter()
            rvListDlc.adapter = dlcAdapter
        }
    }

    private fun setStoreObserver(gameData: GameDetailedEntity) {
        gameDetailViewModel.getStoreLiveData().observe(this) { storeEntity ->
            if (storeEntity.isEmpty() || gameData.store.isEmpty()) return@observe
            storeEntity.forEachIndexed { index, item -> item.name = gameData.store[index].name }
            gameStoreLinkAdapter.addData(storeEntity.toMutableList())
        }
    }

    private fun setScreenshootObserver(id: Long) {
        lifecycleScope.launch {
            gameDetailViewModel.getGameDetailedScreenshoot(id).collectLatest {
                screenShotsAdapter.updateData(it)
            }
        }
    }

    private fun setObserver() {
        gameDetailViewModel.getStatusLoading().observe(this) { if (it) loadingDialog.show() else loadingDialog.dismiss() }

        gameDetailViewModel.getDetailedGameData().observe(this){ gameData ->
            if (gameData == null) return@observe
            getStoreLink(gameData.id)
            setScreenshootObserver(gameData.id)
            setStoreObserver(gameData)
            setDlcObserver(gameData.id)
            setView(gameData)
            gameDetailViewModel.setStatusLoading(false)
        }
    }

    private fun setDlcObserver(id: Long) {
        lifecycleScope.launch {
            gameDetailViewModel.getGameDlc(this, id).collectLatest {
                dlcAdapter.submitData(lifecycle, it)
            }
        }
    }

    private fun setView(data: GameDetailedEntity) {
        binding.apply {

            gameDetailViewModel.getLibraryStatus().observe(this@GameDetailActivity) {
                data.isInLibrary = it
                setLibraryBtn(btnLibrary, data.isInLibrary)
                gameDetailViewModel.setStatusLoading(false)
            }

            setImagePoster(ivGameImage, data)
            setMetacritics(tvMetacritic, data)
            setLibraryBtn(btnLibrary, data.isInLibrary)

            tvGameReleasedDate.text = data.getDetailReleasedDate()
            tvGameTitle.text = data.name
            tvGameDesc.text = data.desc.fromHtml()
            tvGameAgeRating.text = GameDetailedEntity.getNullableString(data.ageRating)
            tvGameGenre.text = GameDetailedEntity.getNullableString(data.genres.joinToString { it.genreName })
            tvGamePlatform.text = GameDetailedEntity.getNullableString(data.platforms.joinToString { it.platform })
            tvGameDeveloper.text = GameDetailedEntity.getNullableString(data.developer.joinToString { it.name })
            tvGamePublisher.text = GameDetailedEntity.getNullableString(data.publishers.joinToString { it.publisher })
            tvGameTags.text = GameDetailedEntity.getNullableString(data.tags.joinToString { it.tagName })

            btnLibrary.setOnClickListener {
                gameDetailViewModel.setStatusLoading(true)
                gameDetailViewModel.manageLibrary(GameItemEntity.transformFromDetail(data))
            }
        }
    }

    private fun setLibraryBtn(btnLibrary: AppCompatButton, inLibrary: Boolean) {
        btnLibrary.text = getString(if (inLibrary) R.string.added_to_library else R.string.add_to_library)
        btnLibrary.setCompoundDrawablesWithIntrinsicBounds(
            if (inLibrary) R.drawable.baseline_check_box_24 else R.drawable.baseline_library_add_24,
            0,
            0,
            0
        )
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
            screenShotsAdapter = GameScreenShotAdapter(this@GameDetailActivity)
            vpImageSlider.apply{
                offscreenPageLimit = 5
                (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                setTransformation(this)
                adapter = screenShotsAdapter
            }
        }
    }

    private fun setImagePoster(ivGameImage: ImageView, data: GameDetailedEntity) {
        Picasso.get().load(data.poster).apply {
            placeholder(Utils.createLoadingImage(this@GameDetailActivity))
            error(R.drawable.baseline_broken_image_24)
            into(ivGameImage)
        }
    }

    private fun setMetacritics(tvMetacritic: TextView, data: GameDetailedEntity) {
        val metaCritic = data.getMetacritics()
        if (metaCritic.isBlank()) return
        tvMetacritic.visibility = View.VISIBLE
        tvMetacritic.text = getString(R.string.metacritic, GameItemEntity.getNullableString(data.metaCritic.toString()))
        tvMetacritic.setTextColor(getColor(data.getMetacriticColor()))
    }

    private fun getStoreLink(id: Long) {
        gameDetailViewModel.getGameStoreLink(id)
    }

    private fun reqItem(id: Long) {
        gameDetailViewModel.setStatusLoading(true)
        gameDetailViewModel.getGameDetail(id)
    }

    override fun onStoreClicked(position: Int) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(gameStoreLinkAdapter.getUrl(position)))
        startActivity(intent)
    }

    override fun onImageClick(position: Int) {
        screenShotsAdapter.zoomIn(position, this)
    }
}