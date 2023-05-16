package com.example.videogameapp.presentation.view.libraryview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.videogameapp.R
import com.example.videogameapp.databinding.ItemGameBinding
import com.example.videogameapp.domain.entity.gameentity.GameItemEntity
import com.squareup.picasso.Picasso

class LibraryAdapter(private val listener: SetOnItemClicked): RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder>() {
    private lateinit var context: Context

    private val diffCallBack = object : DiffUtil.ItemCallback<GameItemEntity>(){
        override fun areItemsTheSame(oldItem: GameItemEntity, newItem: GameItemEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GameItemEntity, newItem: GameItemEntity): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this,diffCallBack)

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
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.binding.apply {
            Picasso.get().load(data.backgroundImage).apply {
                resize(300, 300).placeholder(R.drawable.baseline_broken_image_24)
                into(ivPoster)
            }

            tvGameTitle.text = data.name
            tvRatings.text = context.getString(R.string.rating, data.ratings)
            tvRatings.setTextColor(context.getColor(data.getReviewColor(context)))
            tvGenres.text = context.getString(R.string.genre_genrenya, data.genres)
            tvPlatforms.text = context.getString(R.string.platform, data.platforms)
            tvReleasedDate.text = context.getString(R.string.released_date, data.getReleasedDate())

            setMetacritics(tvMetacritic, data)

            setButton(btnLibrary)

            root.setOnClickListener {
                listener.onItemClicked(position)
            }
            btnLibrary.setOnClickListener {
                listener.onLibraryDelete(position)
            }
        }
    }

    private fun setButton(btnLibrary: ImageButton) {
        btnLibrary.visibility = View.VISIBLE
        btnLibrary.setImageResource(R.drawable.baseline_delete_forever_24)
    }

    private fun setMetacritics(tvMetacritic: TextView, data: GameItemEntity) {
        val metacritic = data.getMetacritics()
        if (metacritic.isBlank()) return
        tvMetacritic.visibility = View.VISIBLE
        tvMetacritic.text = context.getString(R.string.metacritic, data.metaCritic)
        tvMetacritic.setTextColor(context.getColor(data.getMetacriticColor()))
    }

    fun getGameId(position: Int): Long {
        return differ.currentList[position].id
    }

    fun updateList(newList: List<GameItemEntity>) {
        differ.submitList(newList)
    }

    fun getGameItem(position: Int): GameItemEntity {
        return differ.currentList[position]
    }

}