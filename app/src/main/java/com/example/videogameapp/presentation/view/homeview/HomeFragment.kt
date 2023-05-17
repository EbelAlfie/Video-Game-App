package com.example.videogameapp.presentation.view.homeview

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.videogameapp.R
import com.example.videogameapp.Utils
import com.example.videogameapp.databinding.FragmentHomeBinding
import com.example.videogameapp.domain.entity.gameentity.QueryGameItemEntity
import com.example.videogameapp.domain.entity.queryentity.QueryEntity
import com.example.videogameapp.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment (private var viewModel: HomeViewModel, private var queryParam: QueryGameItemEntity = QueryGameItemEntity(null, null, null, null, null, 10)): Fragment(),
    GamePagingAdapter.SetOnItemClicked {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var pagingAdapter: GamePagingAdapter
    private lateinit var loadingDialog: AlertDialog
    private lateinit var orderAdapter: ArrayAdapter<CharSequence>
    private lateinit var platformAdapter: ArrayAdapter<QueryEntity>
    private lateinit var genresAdapter: ArrayAdapter<QueryEntity>

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
        setSpinnerData()
        getData(queryParam)
    }

    private fun setSpinnerData() {
        viewModel.getGenresSpinnerData.observe(requireActivity()) {
            genresAdapter.addAll(it)
        }
        viewModel.getPlatformSpinnerData.observe(requireActivity()) {
            platformAdapter.addAll(it)
        }
    }

    private fun setSpinner() {
        binding.apply {
            orderAdapter = ArrayAdapter.createFromResource(
                requireActivity(),
                R.array.order_by,
                android.R.layout.simple_spinner_item
            )
            genresAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item)
            platformAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item)
            spOrderBy.adapter = orderAdapter
            spPlatform.adapter = platformAdapter
            spGenre.adapter = genresAdapter
        }

    }

    private fun setObserver() {
        viewModel.getStatusLoading().observe(requireActivity()) {
            if (it) loadingDialog.show() else loadingDialog.cancel()
        }
    }

    private fun getData(queryParam: QueryGameItemEntity) {
        viewModel.setStatusLoading(true)
        lifecycleScope.launch {
            viewModel.getGameList(this, queryParam).collectLatest {
                viewModel.setStatusLoading(false)
                pagingAdapter.submitData(lifecycle, it)
            }
        }
        viewModel.getStatusLoading().observe(requireActivity()) {
            if (it) loadingDialog.show() else loadingDialog.dismiss()
        }
    }

    private fun initQueryGameItemParam(search : String?, dates: String?, platform: String?, store: String?, ordering: String?, page: Int): QueryGameItemEntity {
        return QueryGameItemEntity(
            search = search,
            dates = dates,
            platform = platform,
            store = store,
            ordering = ordering,
            page = page
        )
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
                        val queryEntity = initQueryGameItemParam(query, null, null, null, null, 10)
                        getData(queryEntity)
                    }
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean { return true }
            })
        }
    }

    override fun onItemClicked(position: Int) {
        val intent = Utils.generateIntent(requireContext(), pagingAdapter.getGameItemId(position), GameDetailActivity::class.java)
        startActivity(intent)
    }
}