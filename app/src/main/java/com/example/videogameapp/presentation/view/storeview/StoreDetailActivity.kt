package com.example.videogameapp.presentation.view.storeview

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.videogameapp.RawgApp
import com.example.videogameapp.Utils
import com.example.videogameapp.Utils.fromHtml
import com.example.videogameapp.databinding.ActivityStoreDetailBinding
import com.example.videogameapp.domain.entity.gameentity.QueryGameItemEntity
import com.example.videogameapp.domain.entity.storeentity.StoreDetailEntity
import com.example.videogameapp.domain.entity.storeentity.StoreItemEntity
import com.example.videogameapp.presentation.view.homeview.GamePagingAdapter
import com.example.videogameapp.presentation.view.homeview.SubGameFragment
import com.example.videogameapp.presentation.viewmodel.HomeViewModel
import com.example.videogameapp.presentation.viewmodel.StoreViewModel
import com.example.videogameapp.presentation.viewmodel.ViewModelFactory
import com.squareup.picasso.Picasso
import javax.inject.Inject

class StoreDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoreDetailBinding
    private lateinit var loadingDialog: AlertDialog

    @Inject
    lateinit var vmFactory: ViewModelFactory

    private val storeDetailViewModel: StoreViewModel by viewModels { vmFactory }

    private val homeViewModel: HomeViewModel by viewModels { vmFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as RawgApp).appComponent.injectStoreDetail(this)
        super.onCreate(savedInstanceState)
        binding = ActivityStoreDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getLongExtra(Utils.ID_KEY, -1L)

        val storeItemEntity = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Utils.OBJ_KEY, StoreItemEntity::class.java)
        } else { intent.getParcelableExtra(Utils.OBJ_KEY) }

        setLoading()
        setObserver()
        if (id == -1L || storeItemEntity == null) return
        setViews(storeItemEntity)
        getDescrption(id)
        getGameList(id)
    }

    private fun setLoading() {
        loadingDialog = Utils.createLoading(this).create()
    }

    private fun getGameList(id: Long) {
        binding.apply {
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction().replace(flFragmentGameList.id, SubGameFragment(homeViewModel, QueryGameItemEntity("", "", "", id.toString(), "", 5))).commit()
        }
        storeDetailViewModel.setStatusLoading(false)
    }

    private fun setViews(it: StoreItemEntity) {
        binding.apply {
            Picasso.get().load(it.image).apply{
                resize(300,300)
                into(ivStorePoster)
            }
            tvStoreTitle.text = it.name
        }
    }

    private fun setObserver() {
        storeDetailViewModel.getStatusLoading().observe(this) {
            if (it) loadingDialog.show() else loadingDialog.cancel()
        }

        storeDetailViewModel.getStoreDetailData().observe(this) {
            if (it == null) return@observe
            setDesc(it)
        }
    }

    private fun setDesc(it: StoreDetailEntity) {
        binding.tvStoreDesc.text = it.desc.fromHtml()
    }

    private fun getDescrption(id: Long) {
        storeDetailViewModel.setStatusLoading(true)
        storeDetailViewModel.getDetailedStoreData(id)
    }
}