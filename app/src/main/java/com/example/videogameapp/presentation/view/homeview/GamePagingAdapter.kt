package com.example.videogameapp.presentation.view.homeview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.videogameapp.R
import com.example.videogameapp.databinding.ItemGameBinding
import com.example.videogameapp.domain.entity.gameentity.GameItemEntity
import com.squareup.picasso.Picasso

class GamePagingAdapter(private val listener: SetOnItemClicked): PagingDataAdapter<GameItemEntity, GamePagingAdapter.GameViewHolder>(
    DiffCallback
) {
    private lateinit var context: Context

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
            if (data.backgroundImage.isNotBlank()) {
                Picasso.get().load(data.backgroundImage).apply {
                    //resize(250, 250)
                    into(ivPoster)
                }
            }else ivPoster.setImageResource(R.drawable.baseline_broken_image_24)

            tvGameTitle.text = data.name
            tvRatings.text = context.getString(R.string.rating, data.ratings)
            tvRatings.setTextColor(context.getColor(data.getReviewColor(context)))
            tvGenres.text = context.getString(R.string.genre_genrenya, data.genres)
            tvPlatforms.text = context.getString(R.string.platform, data.platforms)
            tvReleasedDate.text = context.getString(R.string.released_date, data.getReleasedDate())

            setMetacritics(tvMetacritic, data)

            root.setOnClickListener {
                listener.onItemClicked(position)
            }
        }
    }

    private fun setMetacritics(tvMetacritic: TextView, data: GameItemEntity) {
        val metacritic = data.getMetacritics()
        if (metacritic.isBlank()) return
        tvMetacritic.visibility = View.VISIBLE
        tvMetacritic.text = context.getString(R.string.metacritic, data.metaCritic)
        tvMetacritic.setTextColor(context.getColor(data.getMetacriticColor()))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        context = parent.context
        return GameViewHolder(
            ItemGameBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    fun getGameItemId(position: Int): Long {
        return getItem(position)?.id ?: -1L
    }

}