package com.example.videogameapp.presentation.view.libraryview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.videogameapp.Utils
import com.example.videogameapp.databinding.FragmentLibraryBinding
import com.example.videogameapp.presentation.view.homeview.GameDetailActivity
import com.example.videogameapp.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LibraryFragment(private val viewModel: HomeViewModel) : Fragment(), LibraryAdapter.SetOnItemClicked {
    private lateinit var binding: FragmentLibraryBinding
    private lateinit var libraryAdapter: LibraryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibraryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecView()
        getData()
    }

    private fun getData() {
        lifecycleScope.launch {
            viewModel.getLibraryData().collectLatest {
                libraryAdapter.updateList(it)
            }
        }
    }

    private fun setRecView() {
        binding.apply {
            rvGameList.layoutManager = GridLayoutManager(requireContext(), 2)
            libraryAdapter = LibraryAdapter(mutableListOf(), this@LibraryFragment)
            rvGameList.adapter = libraryAdapter
        }
    }

    override fun onItemClicked(position: Int) {
        val intent = Utils.generateIntent(requireContext(), libraryAdapter.getGameId(position), GameDetailActivity::class.java)
        startActivity(intent)
    }

    override fun onLibraryDelete(position: Int) {
        TODO("Not yet implemented")
    }
}