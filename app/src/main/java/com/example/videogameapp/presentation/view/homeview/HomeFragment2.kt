package com.example.videogameapp.presentation.view.homeview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.videogameapp.databinding.FragmentHome2Binding
import com.example.videogameapp.domain.entity.gameentity.QueryGameItemEntity
import com.example.videogameapp.presentation.viewmodel.HomeViewModel

class HomeFragment2 (private val viewModel: HomeViewModel): Fragment() {
    private lateinit var binding: FragmentHome2Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHome2Binding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewSetup()
    }

    private fun viewSetup() {
        binding.apply {
            setFragment(flFragmentPopular.id, SubGameFragment(viewModel, QueryGameItemEntity("", "", "", "", "popularity", 5)))
            setFragment(flFragmentLast30Day.id, SubGameFragment(viewModel, QueryGameItemEntity("", "", "", "", "", 5)))
        }
    }

    private fun setFragment(fragmentContainerId: Int, fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        fragmentManager.beginTransaction().replace(fragmentContainerId, fragment).commit()
    }


}