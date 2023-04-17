package com.example.videogameapp.presentation.view.storeview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.videogameapp.Utils
import com.example.videogameapp.databinding.FragmentStoreBinding
import com.example.videogameapp.presentation.viewmodel.StoreViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class StoreFragment(private val viewModel: StoreViewModel) : Fragment(), StoreAdapter.SetOnCLick {
    private lateinit var binding: FragmentStoreBinding
    private lateinit var storeAdapter: StoreAdapter

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
        setViews()
        getData()
    }

    private fun getData() {
        lifecycleScope.launch {
            viewModel.getAllStore(this).collectLatest {
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
    }

    override fun onItemClicked(position: Int) {
        val intent = Utils.generateIntent(requireContext(), storeAdapter.getStoreId(position), StoreDetailActivity::class.java)
        intent.putExtra(Utils.OBJ_KEY, storeAdapter.getStoreItem(position))
        startActivity(intent)
    }
}