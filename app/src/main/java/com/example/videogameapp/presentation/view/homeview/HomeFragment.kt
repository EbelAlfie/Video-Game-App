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
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.videogameapp.R
import com.example.videogameapp.Utils
import com.example.videogameapp.databinding.FragmentHomeBinding
import com.example.videogameapp.domain.entity.gameentity.QueryGameItemEntity
import com.example.videogameapp.presentation.view.MainActivity
import com.example.videogameapp.presentation.view.searchadapter.CustomSpinnerAdapter
import com.example.videogameapp.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

class HomeFragment(private var queryParam: QueryGameItemEntity = QueryGameItemEntity(null, null, null, null, null, null, pageSize = Utils.MODE_ALL_PAGE)): Fragment(),
    GamePagingAdapter.SetOnItemClicked {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var pagingAdapter: GamePagingAdapter
    private lateinit var loadingDialog: AlertDialog
    private lateinit var orderAdapter: ArrayAdapter<CharSequence>
    private lateinit var platformAdapter: CustomSpinnerAdapter
    private lateinit var genresAdapter: CustomSpinnerAdapter
    private lateinit var viewModel: HomeViewModel

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
        fetchViewModel()
        setOtherObserver()
        viewModel.setStatusLoading(true)
        initViews()
        setSpinner()
        setSpinnerObserver()
        setToolbar()
        setObserver()
        viewModel.checkNetworkState(requireContext(), queryParam, fun(queryParam) { getData(queryParam)})
    }

    private fun getData(queryParam: QueryGameItemEntity) {
        viewModel.getSpinnerData()
        getListData(queryParam)
    }

    private fun setOtherObserver() {
        loadingDialog = Utils.createLoading(requireContext()).create()
        viewModel.getStatusLoading().observe(viewLifecycleOwner) {
            if (it) loadingDialog.show() else loadingDialog.dismiss()
        }
        viewModel.getPagingItemCount.observe(viewLifecycleOwner) {
            binding.apply {
                tvNoData.visibility =
                    if (it < 1) View.VISIBLE else View.INVISIBLE
                rvGameList.visibility =
                    if (it < 1) View.INVISIBLE else View.VISIBLE
            }
        }
    }

    private fun setToolbar() {
        (requireActivity() as MainActivity).getToolbar().btnRefresh.setOnClickListener {
            queryData()
        }
    }

    private fun fetchViewModel() {
        viewModel = (requireActivity() as MainActivity).fetchHomeViewModel()
    }

    private fun getListData(queryParam: QueryGameItemEntity) {
        viewModel.setStatusLoading(true)
        queryParam.apply {
            viewModel.initQueryGameItemParam(
                search = search,
                ordering = ordering,
                platform = platform,
                genres = genres,
                pageSize = pageSize
            )
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
            genresAdapter = CustomSpinnerAdapter(requireContext(), mutableListOf())
            platformAdapter = CustomSpinnerAdapter(requireContext(), mutableListOf())

            spPlatform.adapter = platformAdapter
            spGenre.adapter = genresAdapter
            spOrderBy.adapter = orderAdapter
            setSpinnerOrderListener()
        }
    }

    private fun setSpinnerObserver() {
        viewModel.getGenresSpinnerData.observe(requireActivity()) {
            genresAdapter.addAll(it)
            genresAdapter.notifyDataSetChanged()
            setSpinnerGenreListener()
        }
        viewModel.getPlatformSpinnerData.observe(requireActivity()) {
            platformAdapter.addAll(it)
            platformAdapter.notifyDataSetChanged()
            setSpinnerPlatformListener()
        }
    }

    private fun setObserver() {
        viewModel.getListGameData(requireActivity().resources).observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch {
                pagingAdapter.submitData(it)
            }
        }
    }

    private fun initViews() {
        binding.apply {
            rvGameList.layoutManager = GridLayoutManager(requireContext(), 2)
            pagingAdapter = GamePagingAdapter(this@HomeFragment)
            rvGameList.adapter = pagingAdapter

            pagingAdapter.addLoadStateListener {
                viewModel.setItemCount(pagingAdapter.itemCount)
                viewModel.setStatusLoading(false)
                when {
                    (it.prepend is LoadState.Error) -> { setError((it.prepend as LoadState.Error).toString()) }
                    (it.refresh is LoadState.Error) -> { setError((it.refresh as LoadState.Error).toString()) }
                }
            }

            searchView.setOnQueryTextListener(object: OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query?.isNotBlank()!!) {
                        queryParam.apply {
                            search = query
                            ordering = binding.spOrderBy.selectedItem.toString()
                            platform = platformAdapter.getQueryItem(binding.spPlatform.selectedItemPosition)
                            genres = genresAdapter.getQueryItem(binding.spGenre.selectedItemPosition)
                        }
                        queryData()
                    }
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText?.isBlank() == true) {
                        queryParam.apply {
                            search = null
                            ordering = binding.spOrderBy.selectedItem.toString()
                            platform = platformAdapter.getQueryItem(binding.spPlatform.selectedItemPosition)
                            genres = genresAdapter.getQueryItem(binding.spGenre.selectedItemPosition)
                        }
                        queryData()
                    }
                    return true
                }
            })
        }
    }

    private fun setError(msg: String) {
        viewModel.createErrorDialog("Error", msg, requireContext(), fun() { getListData(queryParam) })
    }

    private fun setSpinnerOrderListener() {
        binding.apply {
            spOrderBy.onItemSelectedListener = object: OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    queryParam.search = searchView.query.toString()
                    queryParam.ordering = binding.spOrderBy.selectedItem.toString()
                    queryData()
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
    }

    private fun setSpinnerPlatformListener() {
        binding.apply{
            spPlatform.onItemSelectedListener = object: OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    queryParam.search = searchView.query.toString()
                    queryParam.platform = platformAdapter.getQueryItem(position)
                    queryData()
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
    }

    private fun setSpinnerGenreListener() {
        binding.apply {
            spGenre.onItemSelectedListener = object: OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    queryParam.search = searchView.query.toString()
                    queryParam.genres = genresAdapter.getQueryItem(position)
                    queryData()
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
    }

    private fun queryData() {
        viewModel.checkNetworkState(requireContext(), queryParam, fun(queryParam) {getListData(queryParam)})
    }

    override fun onItemClicked(position: Int) {
        val intent = Utils.generateIntent(requireContext(), pagingAdapter.getGameItemId(position), GameDetailActivity::class.java)
        startActivity(intent)
    }
}