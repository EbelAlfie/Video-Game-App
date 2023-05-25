package com.example.videogameapp.presentation.view.storeview

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.videogameapp.Utils
import com.example.videogameapp.databinding.FragmentStoreBinding
import com.example.videogameapp.domain.entity.gameentity.QueryGameItemEntity
import com.example.videogameapp.presentation.view.MainActivity
import com.example.videogameapp.presentation.viewmodel.StoreViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class StoreFragment : Fragment(), StoreAdapter.SetOnCLick {
    private lateinit var binding: FragmentStoreBinding
    private lateinit var storeAdapter: StoreAdapter
    private lateinit var loadingDialog: AlertDialog
    private lateinit var viewModel: StoreViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoreBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewModel()
        setViews()
        setObserver()
        setToolbar()
        viewModel.checkNetworkState(requireContext(), fun(){ getData() })
    }

    private fun setToolbar() {
        (requireActivity() as MainActivity).getToolbar().btnRefresh.setOnClickListener {
            viewModel.checkNetworkState(requireContext(), fun(){ getData() })
        }
    }

    private fun getViewModel() {
        viewModel = (requireActivity() as MainActivity).fetchStoreViewModel()
    }

    private fun setObserver() {
        viewModel.getStatusLoading().observe(requireActivity()) {
            if (it) loadingDialog.show() else loadingDialog.cancel()
        }
    }

    private fun getData() {
        viewModel.setStatusLoading(true)
        lifecycleScope.launch {
            viewModel.getAllStore(this).collectLatest {
                binding.apply {
                    if (it.isNotEmpty()) {
                        tvNoData.visibility = View.GONE
                        rvStoreList.visibility = View.VISIBLE
                    }else {
                        tvNoData.visibility = View.VISIBLE
                        rvStoreList.visibility = View.GONE
                    }
                }
                viewModel.setStatusLoading(false)
                storeAdapter.updateList(it)
            }
        }
    }

    private fun setViews() {
        binding.apply{
            rvStoreList.layoutManager = LinearLayoutManager(requireContext())
            storeAdapter = StoreAdapter(listOf(), this@StoreFragment)
            rvStoreList.adapter = storeAdapter
        }
        loadingDialog = Utils.createLoading(requireContext()).create()
    }

    override fun onItemClicked(position: Int) {
        val intent = Utils.generateIntent(requireContext(), storeAdapter.getStoreId(position), StoreDetailActivity::class.java)
        intent.putExtra(Utils.OBJ_KEY, storeAdapter.getStoreItem(position))
        startActivity(intent)
    }
}