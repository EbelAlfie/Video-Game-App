package com.example.videogameapp.presentation.view.homeview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.videogameapp.R
import com.example.videogameapp.databinding.ItemScreenshotBinding
import com.example.videogameapp.domain.entity.gameentity.TrailerEntity
import com.squareup.picasso.Picasso

class TrailerAdapter(private val listTrailer: MutableList<TrailerEntity>): RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>() {
    class TrailerViewHolder(val binding: ItemScreenshotBinding): ViewHolder(binding.root)

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        val data = listTrailer[position]
        holder.binding.apply {
            Picasso.get().load(data.previewImage).placeholder(R.drawable.baseline_broken_image_24).into(ivScreenshot)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
        return TrailerViewHolder(ItemScreenshotBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return listTrailer.size
    }

    fun submitData(newList: List<TrailerEntity>) {
        listTrailer.clear()
        listTrailer.addAll(newList)
        notifyItemRangeChanged(0, newList.size)
    }
}