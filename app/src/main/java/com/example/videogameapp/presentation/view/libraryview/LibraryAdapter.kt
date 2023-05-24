package com.example.videogameapp.presentation.view.libraryview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.videogameapp.R
import com.example.videogameapp.databinding.ItemGameBinding
import com.example.videogameapp.domain.entity.gameentity.GameItemEntity
import com.squareup.picasso.Picasso

class LibraryAdapter(private var inList: MutableList<GameItemEntity>, private val listener: SetOnItemClicked): Adapter<LibraryAdapter.LibraryViewHolder>() {
    private lateinit var context: Context

    /*private class DiffUtilCallback(val oldList: MutableList<GameItemEntity>, val newList: MutableList<GameItemEntity>): DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

    }*/

    /*class DiffComparator: DiffUtil.ItemCallback<GameItemEntity>() {
        override fun areItemsTheSame(oldItem: GameItemEntity, newItem: GameItemEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GameItemEntity, newItem: GameItemEntity): Boolean {
            return oldItem == newItem
        }

    }*/

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
        return inList.size
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        val data = inList[position]
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
        tvMetacritic.text = context.getString(R.string.metacritic, GameItemEntity.getNullableString(metacritic))
        tvMetacritic.setTextColor(context.getColor(data.getMetacriticColor()))
    }

    fun getGameId(position: Int): Long {
        return inList[position].id
    }

    fun updateList(newList: MutableList<GameItemEntity>) {
        inList.clear()
        inList.addAll(newList)
        notifyDataSetChanged()
    }

    fun getGameItem(position: Int): GameItemEntity {
        return inList[position]
    }

}