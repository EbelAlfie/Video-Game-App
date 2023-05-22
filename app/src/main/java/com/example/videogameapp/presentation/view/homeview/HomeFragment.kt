package com.example.videogameapp.presentation.view.homeview

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.videogameapp.R
import com.example.videogameapp.Utils
import com.example.videogameapp.databinding.FragmentHomeBinding
import com.example.videogameapp.domain.entity.gameentity.QueryGameItemEntity
import com.example.videogameapp.presentation.view.searchadapter.CustomSpinnerAdapter
import com.example.videogameapp.presentation.viewmodel.HomeViewModel

class HomeFragment (private var viewModel: HomeViewModel, private var queryParam: QueryGameItemEntity = QueryGameItemEntity(null, null, null, null, null, null, 10)): Fragment(),
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
        viewModel.initQueryGameItemParam(queryParam)
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
            setSpinnerListener()
        }
    }

    private fun setSpinner() {
        binding.apply {
            orderAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.order_by,
                R.layout.custom_spinner
            )
            orderAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown)
            genresAdapter = CustomSpinnerAdapter(requireContext(), R.layout.custom_spinner, mutableListOf())
            platformAdapter = CustomSpinnerAdapter(requireContext(), R.layout.custom_spinner, mutableListOf())

            spPlatform.adapter = platformAdapter
            spGenre.adapter = genresAdapter
            spOrderBy.adapter = orderAdapter
        }

    }

    private fun setObserver() {
        loadingDialog = Utils.createLoading(requireContext()).create()
        viewModel.getStatusLoading().observe(requireActivity()) {
            if (it) loadingDialog.show() else loadingDialog.dismiss()
        }

        viewModel.getListGameData().observe(requireActivity()) {
            pagingAdapter.submitData(lifecycle, it)
            viewModel.setStatusLoading(false)
        }
    }

    private fun initViews() {
        binding.apply {
            rvGameList.layoutManager = GridLayoutManager(requireContext(), 2)
            pagingAdapter = GamePagingAdapter(this@HomeFragment)
            rvGameList.adapter = pagingAdapter


            searchView.setOnQueryTextListener(object: OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query?.isNotBlank()!!) {
                        queryData(query)
                    }
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText?.isBlank() == true) {
                        queryData("")
                    }
                    return true
                }
            })
        }
    }

    private fun setSpinnerListener() {
        binding.apply {
            spOrderBy.onItemSelectedListener = object: OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) { queryData(searchView.query.toString()) }
                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }

            spGenre.onItemSelectedListener = object: OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) { queryData(searchView.query.toString()) }
                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }

            spPlatform.onItemSelectedListener = object: OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) { queryData(searchView.query.toString()) }
                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
    }

    private fun queryData(search: String) {
        binding.apply {
            viewModel.initQueryGameItemParam(QueryGameItemEntity(
                search = search,
                ordering = binding.spOrderBy.selectedItem.toString(),
                platform = platformAdapter.getQueryItem(binding.spPlatform.selectedItemPosition),
                genres = genresAdapter.getQueryItem(binding.spGenre.selectedItemPosition),
                page = 10
            ))
        }
    }

    override fun onItemClicked(position: Int) {
        val intent = Utils.generateIntent(requireContext(), pagingAdapter.getGameItemId(position), GameDetailActivity::class.java)
        startActivity(intent)
    }
}