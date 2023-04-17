package com.example.videogameapp.presentation.view.homeview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.videogameapp.databinding.ItemScreenshotBinding
import com.example.videogameapp.domain.entity.gameentity.ScreenShotEntity
import com.squareup.picasso.Picasso

class GameScreenShotAdapter(private val screenshots: MutableList<ScreenShotEntity?>): Adapter<GameScreenShotAdapter.ScreenShotViewHolder>() {
    class ScreenShotViewHolder(val binding: ItemScreenshotBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenShotViewHolder {
        return ScreenShotViewHolder(ItemScreenshotBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return screenshots.size
    }

    override fun onBindViewHolder(holder: ScreenShotViewHolder, position: Int) {
        val data = screenshots[position]
        holder.binding.apply {
            Picasso.get().load(data?.image).apply {
                resize(600, 400)
                into(ivScreenshot)
            }
        }
    }

    fun updateList(newList: List<ScreenShotEntity>) {
        screenshots.clear()
        screenshots.addAll(newList)
        notifyItemRangeChanged(0, screenshots.size)
    }

}