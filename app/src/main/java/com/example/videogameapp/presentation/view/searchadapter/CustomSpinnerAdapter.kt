package com.example.videogameapp.presentation.view.searchadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.videogameapp.databinding.CustomSpinnerBinding
import com.example.videogameapp.databinding.CustomSpinnerDropdownBinding
import com.example.videogameapp.domain.entity.queryentity.QueryEntity

class CustomSpinnerAdapter (context: Context, resource: Int, private val listItem: MutableList<QueryEntity>): ArrayAdapter<QueryEntity>(context, resource, listItem) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: CustomSpinnerBinding =
            if (convertView != null) CustomSpinnerBinding.bind(convertView)
            else CustomSpinnerBinding.inflate(LayoutInflater.from(context), parent, false)
        val item = getItem(position)
        binding.tvItemSpinner.text = item?.name ?: ""
        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: CustomSpinnerDropdownBinding =
            if (convertView != null) CustomSpinnerDropdownBinding.bind(convertView)
            else CustomSpinnerDropdownBinding.inflate(LayoutInflater.from(context), parent, false)
        val item = getItem(position)
        binding.tvItemSpinner.text = item?.name ?: ""
        return binding.root
    }

    override fun getItem(position: Int): QueryEntity? {
        return if (listItem.isEmpty()) null else listItem[position]
    }

    fun getQueryItem(position: Int): String {
        return getItem(position)?.id.toString()
    }
}
