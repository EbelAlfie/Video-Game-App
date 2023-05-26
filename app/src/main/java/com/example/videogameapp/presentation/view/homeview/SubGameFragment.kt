package com.example.videogameapp.presentation.view.homeview

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.videogameapp.R
import com.example.videogameapp.Utils
import com.example.videogameapp.databinding.FragmentSubGameItemBinding
import com.example.videogameapp.domain.entity.gameentity.QueryGameItemEntity
import com.example.videogameapp.presentation.view.storeview.StoreDetailActivity
import com.example.videogameapp.presentation.viewmodel.StoreViewModel

class SubGameFragment (private val queryParam: QueryGameItemEntity): Fragment(), GamePagingAdapter.SetOnItemClicked {
    private lateinit var binding: FragmentSubGameItemBinding
    private lateinit var gameAdapter: GamePagingAdapter
    private lateinit var loadingDialog: AlertDialog
    private lateinit var viewModel: StoreViewModel

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
        getViewModel()
        setUiObserver()
        setView()
        setObserver()
        queryData()
    }

    private fun setUiObserver() {
        viewModel.getStatusLoading().observe(requireActivity()){ if (it) loadingDialog.show() else loadingDialog.dismiss() }
    }

    private fun getViewModel() {
        viewModel = (requireActivity() as StoreDetailActivity).provideViewModel()
    }

    private fun setObserver() {
        viewModel.getListGameData(requireActivity().resources).observe(viewLifecycleOwner) {
            gameAdapter.submitData(lifecycle, it)
        }
        viewModel.getPagingItemCount.observe(requireActivity()) {

        }
    }

    private fun setView() {
        binding.apply {
            rvSubGameList.layoutManager = GridLayoutManager(requireContext(), 2)
            gameAdapter = GamePagingAdapter(this@SubGameFragment)
            rvSubGameList.adapter = gameAdapter

            gameAdapter.addLoadStateListener {
                viewModel.setItemCount(gameAdapter.itemCount)
                viewModel.setStatusLoading(false)
                when {
                    (it.prepend is LoadState.Error) -> { setError((it.prepend as LoadState.Error).toString()) }
                    (it.refresh is LoadState.Error) -> { setError((it.refresh as LoadState.Error).toString()) }
                }
            }
            setSearchView()
            setSpinner()
        }
    }

    private fun setError(msg: String) {
        Utils.createErrorDialog("Error", msg, requireContext(), fun() { queryData() })
    }

    private fun setSpinner() {
        binding.apply {
            val orderAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.order_by,
                R.layout.custom_spinner
            )
            orderAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown)
            spOrderBy.adapter = orderAdapter
            setSpinnerOrderListener()
        }
    }

    private fun setSpinnerOrderListener() {
        binding.apply {
            spOrderBy.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    queryParam.search = searchView.query.toString()
                    queryParam.ordering = binding.spOrderBy.selectedItem.toString()
                    queryData()
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
    }

    private fun setSearchView() {
        binding.apply {
            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query?.isNotBlank() == true) {
                        queryParam.apply {
                            search = query
                            ordering = binding.spOrderBy.selectedItem.toString()
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
                        }
                        queryData()
                    }
                    return true
                }
            })
        }
    }

    private fun queryData() {
        queryParam.apply {
            viewModel.initQueryGameItemParam(
                search = search,
                ordering = ordering,
                dates = dates,
                platform = platform,
                store = store,
                genres = genres,
                pageSize = Utils.MODE_ALL_PAGE
            )
        }
    }

    override fun onItemClicked(position: Int) {
        val intent = Utils.generateIntent(requireContext(), gameAdapter.getGameItemId(position), GameDetailActivity::class.java)
        startActivity(intent)
    }
}