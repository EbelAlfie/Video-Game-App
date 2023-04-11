package com.example.videogameapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.videogameapp.Utils
import com.example.videogameapp.databinding.FragmentHomeBinding
import com.example.videogameapp.domain.entity.QueryGameItemEntity
import com.example.videogameapp.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment (private var viewModel: HomeViewModel): Fragment(), GamePagingAdapter.SetOnItemClicked {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var pagingAdapter: GamePagingAdapter

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
        initRecView()
        getData(initQueryGameItemParam(null, null))
    }

    private fun getData(queryParam: QueryGameItemEntity) {
        lifecycleScope.launch {
            viewModel.getGameList(this, queryParam).collectLatest {
                pagingAdapter.submitData(lifecycle, it)
            }
        }
    }

    private fun initQueryGameItemParam(search : String?, dates: String?): QueryGameItemEntity {
        return QueryGameItemEntity(
            search = search,
            dates = dates
        )
    }

    private fun initRecView() {
        binding.apply {
            rvGameList.layoutManager = GridLayoutManager(requireContext(), 2)
            pagingAdapter = GamePagingAdapter(this@HomeFragment)
            rvGameList.adapter = pagingAdapter
            searchView.setOnQueryTextListener(object: OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query?.isNotBlank()!!) {
                        val queryEntity = initQueryGameItemParam(query, null)
                        getData(queryEntity)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }

            })
        }
    }

    override fun onItemClicked(position: Int) {
        Utils.generateIntent(requireContext(), pagingAdapter.getGameId(position), null)
    }
}