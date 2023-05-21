package com.example.videogameapp.presentation.view.searchadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.videogameapp.databinding.CustomSpinnerBinding
import com.example.videogameapp.domain.entity.queryentity.QueryEntity

class CustomSpinnerAdapter (context: Context, resource: Int, private val listItem: MutableList<QueryEntity>): ArrayAdapter<QueryEntity>(context, resource, listItem) {
    //private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return bind(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return bind(position, convertView, parent)
    }

    override fun getItem(position: Int): QueryEntity {
        return listItem[position]
    }

    private fun bind(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: CustomSpinnerBinding =
            if (convertView != null) CustomSpinnerBinding.bind(convertView)
            else CustomSpinnerBinding.inflate(LayoutInflater.from(context), parent, false)
        val item = getItem(position)
        binding.tvItemSpinner.text = item?.name ?: ""
        return binding.root
    }
}
