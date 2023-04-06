package com.example.videogameapp.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.videogameapp.data.modeldata.GameItemModel
import com.example.videogameapp.databinding.ItemGameBinding
import com.example.videogameapp.domain.entity.GameItemEntity
import com.squareup.picasso.Picasso

class GamePagingAdapter(private val listener: SetOnItemClicked): PagingDataAdapter<GameItemEntity, GamePagingAdapter.GameViewHolder>(DiffCallback) {

    interface SetOnItemClicked{
        fun onItemClicked(position: Int)
    }

    companion object {
        object DiffCallback : DiffUtil.ItemCallback<GameItemEntity>() {
            override fun areItemsTheSame(
                oldItem: GameItemEntity,
                newItem: GameItemEntity
            ): Boolean {
                return (oldItem.id == newItem.id)
            }

            override fun areContentsTheSame(
                oldItem: GameItemEntity,
                newItem: GameItemEntity
            ) = oldItem == newItem
        }
    }

    class GameViewHolder(val binding: ItemGameBinding): RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val data = getItem(position) ?: return
        holder.binding.apply {
            Picasso.get().load(data.backgroundImage).into(ivPoster)
            tvGameTitle.text = data.name
            tvPlaytime.text = data.playtime.toString()
            tvReleasedDate.text = data.dateReleased

            root.setOnClickListener {
                listener.onItemClicked(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        return GameViewHolder(ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

}