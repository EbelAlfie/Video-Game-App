package com.example.videogameapp.presentation.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.videogameapp.R
import com.example.videogameapp.data.modeldata.GameItemModel
import com.example.videogameapp.databinding.ItemGameBinding
import com.example.videogameapp.domain.entity.GameItemEntity
import com.squareup.picasso.Picasso

class GamePagingAdapter(private val listener: SetOnItemClicked): PagingDataAdapter<GameItemEntity, GamePagingAdapter.GameViewHolder>(DiffCallback) {
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
            if (data.backgroundImage?.isNotBlank() == true) {
                Picasso.get().load(data.backgroundImage).into(ivPoster)
            }else ivPoster.setImageResource(R.drawable.baseline_broken_image_24)

            tvGameTitle.text = data.name
            tvPlaytime.text = context.getString(R.string.playtime, data.playtime)

            tvRatings.text = context.getString(R.string.rating, data.getRatings())

            setReleasedDateTv(tvReleasedDate, data)

            tvGenres.text = context.getString(R.string.genre_genrenya, data.genres.joinToString{ it?.genreName ?: "" })

            setMetacritics(tvMetacritic, data)

            tvPlatforms.text = context.getString(R.string.platform, data.platforms.joinToString { it?.platform ?: "" })
            tvReviewCount.text = context.getString(R.string.review_count, data.reviewCount)

            root.setOnClickListener {
                listener.onItemClicked(position)
            }
        }
    }

    private fun setReleasedDateTv(tvReleasedDate: TextView, data: GameItemEntity) {
        tvReleasedDate.text = context.getString(R.string.released_date, if (!data.tbaStatus) data.dateReleased else "TBA")
    }

    private fun setMetacritics(tvMetacritic: TextView, data: GameItemEntity) {
        val metacritic = data.getMetacritics()
        if (metacritic.isBlank()) return
        tvMetacritic.visibility = View.VISIBLE
        tvMetacritic.text = metacritic
        tvMetacritic.setTextColor(data.getColor())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamePagingAdapter.GameViewHolder {
        context = parent.context
        return GameViewHolder(
            ItemGameBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    fun getGameId(position: Int): Long {
        return getItem(position)?.id ?: 0
    }

}