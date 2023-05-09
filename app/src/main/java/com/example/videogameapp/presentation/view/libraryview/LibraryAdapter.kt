package com.example.videogameapp.presentation.view.libraryview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.videogameapp.R
import com.example.videogameapp.databinding.ItemGameBinding
import com.example.videogameapp.domain.entity.gameentity.GameItemEntity
import com.squareup.picasso.Picasso

class LibraryAdapter(private val gameLibraryList: MutableList<GameItemEntity>, private val listener: SetOnItemClicked): RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder>() {
    private lateinit var context: Context
    class LibraryViewHolder(val binding: ItemGameBinding): ViewHolder(binding.root)

    interface SetOnItemClicked{
        fun onItemClicked(position: Int)
        fun onLibraryDelete(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        context = parent.context
        return LibraryViewHolder(ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return gameLibraryList.size
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        val data = gameLibraryList[position]
        holder.binding.apply {
            if (data.backgroundImage.isNotBlank()) {
                Picasso.get().load(data.backgroundImage).apply {
                    resize(300,300)
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
            btnLibrary.setOnClickListener {
                listener.onLibraryDelete(position)
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

    fun getGameId(position: Int): Long {
        return gameLibraryList[position].id
    }

    fun updateList(it: List<GameItemEntity>) {
        gameLibraryList.clear()
        gameLibraryList.addAll(it)
        notifyItemRangeChanged(0, gameLibraryList.size)
    }

}