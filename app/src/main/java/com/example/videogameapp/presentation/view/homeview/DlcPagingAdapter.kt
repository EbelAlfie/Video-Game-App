package com.example.videogameapp.presentation.view.homeview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.videogameapp.R
import com.example.videogameapp.Utils
import com.example.videogameapp.databinding.ItemDlcBinding
import com.example.videogameapp.domain.entity.gameentity.GameItemEntity
import com.squareup.picasso.Picasso

class DlcPagingAdapter: PagingDataAdapter<GameItemEntity, DlcPagingAdapter.DlcViewHolder>(DiffCallback) {
    private lateinit var context: Context
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
    class DlcViewHolder(val binding: ItemDlcBinding): ViewHolder(binding.root)

    override fun onBindViewHolder(holder: DlcViewHolder, position: Int) {
        val data = getItem(position) ?: return
        holder.binding.apply {
            if (data.backgroundImage.isNotBlank()) {
                Picasso.get().load(data.backgroundImage).apply{
                    placeholder(Utils.createLoadingImage(context))
                    error(R.drawable.baseline_broken_image_24)
                    resize(100,100)
                    into(ivDlcPoster)
                }
            }
            tvDlcTitle.text = data.name
            tvDlcReleasedDate.text = data.dateReleased
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DlcViewHolder {
        context = parent.context
        return DlcViewHolder(ItemDlcBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}