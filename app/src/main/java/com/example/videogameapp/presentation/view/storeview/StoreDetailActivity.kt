package com.example.videogameapp.presentation.view.storeview

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.videogameapp.R
import com.example.videogameapp.RawgApp
import com.example.videogameapp.Utils
import com.example.videogameapp.Utils.fromHtml
import com.example.videogameapp.databinding.ActivityStoreDetailBinding
import com.example.videogameapp.domain.entity.gameentity.QueryGameItemEntity
import com.example.videogameapp.domain.entity.storeentity.StoreDetailEntity
import com.example.videogameapp.domain.entity.storeentity.StoreItemEntity
import com.example.videogameapp.presentation.view.homeview.HomeFragment
import com.example.videogameapp.presentation.view.homeview.SubGameFragment
import com.example.videogameapp.presentation.viewmodel.StoreViewModel
import com.example.videogameapp.presentation.viewmodel.ViewModelFactory
import com.squareup.picasso.Picasso
import javax.inject.Inject

class StoreDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoreDetailBinding
    private lateinit var loadingDialog: AlertDialog
    private var id: Long = -1

    @Inject
    lateinit var vmFactory: ViewModelFactory

    private val storeDetailViewModel: StoreViewModel by viewModels { vmFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as RawgApp).appComponent.injectStoreDetail(this)
        super.onCreate(savedInstanceState)
        binding = ActivityStoreDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val storeItemEntity = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Utils.OBJ_KEY, StoreItemEntity::class.java)
        } else { intent.getParcelableExtra(Utils.OBJ_KEY) }

        id = storeDetailViewModel.getStoreId(intent)

        createLoadingProgress()
        setObserver()
        if (id == -1L || storeItemEntity == null) return
        setToolbar()
        setLoading(true)
        setViews(storeItemEntity)
        getDescrption(id)
        getGameList(id)
    }
    private fun setToolbar() {
        binding.toolbar.btnBack.setOnClickListener {
            finish()
        }
        binding.toolbar.btnRefresh.setOnClickListener {
            storeDetailViewModel.checkNetworkState(this, fun() { storeDetailViewModel.getDetailedStoreData(id) })
        }
    }

    private fun setLoading(status: Boolean) {
        storeDetailViewModel.setStatusLoading(status)
    }

    private fun createLoadingProgress() {
        loadingDialog = Utils.createLoading(this).create()
    }

    private fun getGameList(id: Long) {
        binding.apply {
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction().replace(flFragmentGameList.id, SubGameFragment(QueryGameItemEntity(store = id.toString(), pageSize = Utils.MODE_SUB_PAGE))).commit()
        }
    }

    private fun setViews(it: StoreItemEntity) {
        binding.apply {
            Picasso.get().load(it.image).apply{
                placeholder(Utils.createLoadingImage(this@StoreDetailActivity))
                error(R.drawable.baseline_broken_image_24)
                into(ivStorePoster)
            }
            tvStoreTitle.text = it.name
            tvViewMore.setOnClickListener {
                binding.apply {
                    val fragmentManager = supportFragmentManager
                    fragmentManager.beginTransaction().replace(binding.flContainer.id, HomeFragment(QueryGameItemEntity(store = id.toString(), pageSize = Utils.MODE_SUB_PAGE))).commit()
                }
            }
        }
    }

    private fun setObserver() {
        storeDetailViewModel.getStatusLoading().observe(this) {
            if (it) loadingDialog.show() else loadingDialog.dismiss()
        }
        storeDetailViewModel.getStoreDetailData().observe(this) {
            setLoading(false)
            if (it == null) return@observe
            setDesc(it)
        }
    }

    private fun setDesc(it: StoreDetailEntity) {
        binding.tvStoreDesc.text = StoreDetailEntity.getNullableString(it.desc.fromHtml().toString())
    }

    private fun getDescrption(id: Long) {
        storeDetailViewModel.checkNetworkState(this, fun() { storeDetailViewModel.getDetailedStoreData(id) })
    }

    fun provideViewModel(): StoreViewModel {
        return storeDetailViewModel
    }
}