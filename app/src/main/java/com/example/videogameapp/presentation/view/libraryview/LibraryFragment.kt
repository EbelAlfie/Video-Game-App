package com.example.videogameapp.presentation.view.libraryview

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
    private lateinit var dialogConfirmation: AlertDialog
    private lateinit var loadingDialog: AlertDialog

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
        setDialog()
    }

    override fun onResume() {
        super.onResume()
        viewModel.setStatusLoading(true)
        getData()
    }

    private fun setDialog() {
        loadingDialog = Utils.createLoading(requireContext()).create()
        viewModel.getStatusLoading().observe(requireActivity()) {
            if (it) loadingDialog.show() else loadingDialog.dismiss()
        }
    }
    private fun getData() {
        viewModel.updateLiveData().observe(requireActivity()) {
            getData()
        }
        lifecycleScope.launch {
            viewModel.getLibraryData().collectLatest {
                viewModel.setStatusLoading(false)
                if (it.isEmpty()) return@collectLatest
                libraryAdapter.updateList(it.toMutableList())
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
        viewModel.setStatusLoading(true)
        viewModel.manageLibrary(libraryAdapter.getGameItem(position))
    }
}