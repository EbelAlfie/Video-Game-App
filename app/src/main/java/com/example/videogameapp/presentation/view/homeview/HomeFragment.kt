package com.example.videogameapp.presentation.view.homeview

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.videogameapp.R
import com.example.videogameapp.Utils
import com.example.videogameapp.databinding.FragmentHomeBinding
import com.example.videogameapp.domain.entity.gameentity.QueryGameItemEntity
import com.example.videogameapp.domain.entity.queryentity.QueryEntity
import com.example.videogameapp.presentation.view.searchadapter.CustomSpinnerAdapter
import com.example.videogameapp.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

class HomeFragment (private var viewModel: HomeViewModel, private var queryParam: QueryGameItemEntity = QueryGameItemEntity(null, null, null, null, null, 10)): Fragment(),
    GamePagingAdapter.SetOnItemClicked {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var pagingAdapter: GamePagingAdapter
    private lateinit var loadingDialog: AlertDialog
    private lateinit var orderAdapter: ArrayAdapter<CharSequence>
    private lateinit var platformAdapter: CustomSpinnerAdapter
    private lateinit var genresAdapter: CustomSpinnerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setObserver()
        setSpinner()
        viewModel.initQueryGameItemParam(null, null, null, null, binding.spOrderBy.selectedItem.toString(), 10)
        setSpinnerData()
    }

    private fun setSpinnerData() {
        viewModel.getGenresSpinnerData.observe(requireActivity()) {
            genresAdapter.addAll(it)
            genresAdapter.notifyDataSetChanged()
        }
        viewModel.getPlatformSpinnerData.observe(requireActivity()) {
            platformAdapter.addAll(it)
            platformAdapter.notifyDataSetChanged()
        }
    }

    private fun setSpinner() {
        binding.apply {
            orderAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.order_by,
                R.layout.custom_spinner
            )
            genresAdapter = CustomSpinnerAdapter(requireContext(), R.layout.custom_spinner, mutableListOf())
            platformAdapter = CustomSpinnerAdapter(requireContext(), R.layout.custom_spinner, mutableListOf())

            spPlatform.adapter = platformAdapter
            spGenre.adapter = genresAdapter
            spOrderBy.adapter = orderAdapter
        }

    }

    private fun setObserver() {
        viewModel.setStatusLoading(true)
        lifecycleScope.launch {
            viewModel.getListGameData().observe(requireActivity()) {
                viewModel.setStatusLoading(false)
                pagingAdapter.submitData(lifecycle, it)
            }
        }
        viewModel.getStatusLoading().observe(requireActivity()) { if (it) loadingDialog.show() else loadingDialog.dismiss() }

    }

    private fun initViews() {
        binding.apply {
            rvGameList.layoutManager = GridLayoutManager(requireContext(), 2)
            pagingAdapter = GamePagingAdapter(this@HomeFragment)
            rvGameList.adapter = pagingAdapter

            loadingDialog = Utils.createLoading(requireContext()).create()

            searchView.setOnQueryTextListener(object: OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query?.isNotBlank()!!) {
                        viewModel.initQueryGameItemParam(query, null, null, null, binding.spOrderBy.selectedItem.toString(), 10)
                    }
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText?.isBlank() == true) {
                        viewModel.initQueryGameItemParam(null, null, null, null, binding.spOrderBy.selectedItem.toString(), 10)
                    }
                    return true
                }
            })
        }
    }

    override fun onItemClicked(position: Int) {
        val intent = Utils.generateIntent(requireContext(), pagingAdapter.getGameItemId(position), GameDetailActivity::class.java)
        startActivity(intent)
    }
}