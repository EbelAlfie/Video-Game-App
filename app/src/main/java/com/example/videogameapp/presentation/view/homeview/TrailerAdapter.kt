package com.example.videogameapp.presentation.view.homeview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.videogameapp.R
import com.example.videogameapp.Utils
import com.example.videogameapp.databinding.ItemScreenshotBinding
import com.example.videogameapp.domain.entity.gameentity.TrailerEntity
import com.squareup.picasso.Picasso

class TrailerAdapter(private val listTrailer: MutableList<TrailerEntity>): RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>() {
    private lateinit var context: Context
    class TrailerViewHolder(val binding: ItemScreenshotBinding): ViewHolder(binding.root)

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        val data = listTrailer[position]
        holder.binding.apply {
            Picasso.get().load(data.previewImage).apply {
                error(R.drawable.baseline_broken_image_24)
                placeholder(Utils.createLoadingImage(context))
                into(ivScreenshot)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
        context = parent.context
        return TrailerViewHolder(ItemScreenshotBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return listTrailer.size
    }

    fun submitData(newList: List<TrailerEntity>) {
        listTrailer.clear()
        listTrailer.addAll(newList)
        notifyDataSetChanged()
    }
}