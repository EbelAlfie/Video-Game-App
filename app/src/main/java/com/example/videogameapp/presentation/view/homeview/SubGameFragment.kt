package com.example.videogameapp.presentation.view.homeview

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.videogameapp.Utils
import com.example.videogameapp.databinding.FragmentSubGameItemBinding
import com.example.videogameapp.domain.entity.gameentity.QueryGameItemEntity
import com.example.videogameapp.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

class SubGameFragment (private val viewModel: HomeViewModel, private val queryGameItemEntity: QueryGameItemEntity): Fragment(), GamePagingAdapter.SetOnItemClicked {
    private lateinit var binding: FragmentSubGameItemBinding
    private lateinit var gameAdapter: GamePagingAdapter
    private lateinit var loadingDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSubGameItemBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = Utils.createLoading(requireContext()).create()
        viewModel.initQueryGameItemParam(queryGameItemEntity.search, queryGameItemEntity.dates, queryGameItemEntity.platform, queryGameItemEntity.store, queryGameItemEntity.ordering, queryGameItemEntity.page ?: 10)
        setRv()
        setObserver()
    }

    private fun setObserver() {
        lifecycleScope.launch {
            viewModel.getListGameData(this).observe(requireActivity()) {
                gameAdapter.submitData(lifecycle, it)
            }
        }
        viewModel.getStatusLoading().observe(requireActivity()){
            if (it) loadingDialog.show() else loadingDialog.dismiss()
        }
    }

    private fun setRv() {
        binding.apply {
            rvSubGameList.layoutManager = GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false)
            gameAdapter = GamePagingAdapter(this@SubGameFragment)
            rvSubGameList.adapter = gameAdapter
        }
    }

    override fun onItemClicked(position: Int) {
        val intent = Utils.generateIntent(requireContext(), gameAdapter.getGameItemId(position), GameDetailActivity::class.java)
        startActivity(intent)
    }
}