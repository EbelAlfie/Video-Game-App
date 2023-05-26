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
import com.example.videogameapp.presentation.view.MainActivity
import com.example.videogameapp.presentation.view.homeview.GameDetailActivity
import com.example.videogameapp.presentation.viewmodel.LibraryViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LibraryFragment : Fragment(), LibraryAdapter.SetOnItemClicked {
    private lateinit var binding: FragmentLibraryBinding
    private lateinit var libraryAdapter: LibraryAdapter
    private lateinit var loadingDialog: AlertDialog
    private lateinit var viewModel: LibraryViewModel
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
        fetchViewModel()
        setRecView()
        setObserver()
    }

    override fun onResume() {
        super.onResume()
        viewModel.setStatusLoading(true)
        fetchData()
    }
    private fun fetchViewModel() {
        viewModel = (requireActivity() as MainActivity).fetchLibraryViewModel()
    }

    private fun setObserver() {
        loadingDialog = Utils.createLoading(requireContext()).create()
        viewModel.getStatusLoading().observe(requireActivity()) {
            if (it) loadingDialog.show() else loadingDialog.dismiss()
        }
        viewModel.getLibraryStatus.observe(requireActivity()) {
            fetchData()
        }
    }

    private fun fetchData() {
        viewModel.checkNetworkState(requireContext(), fun() { getData() })
    }

    private fun getData() {
        lifecycleScope.launch {
            viewModel.getLibraryData(requireContext()).collectLatest {
                binding.apply {
                    tvNoData.visibility = if (it.isEmpty()) View.VISIBLE else View.INVISIBLE
                    rvGameList.visibility = if (it.isEmpty()) View.INVISIBLE else View.VISIBLE
                }
                libraryAdapter.updateList(it.toMutableList())
                viewModel.setStatusLoading(false)
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