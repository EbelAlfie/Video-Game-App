package com.example.videogameapp.presentation.view.storeview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.videogameapp.databinding.ItemStoreBinding
import com.example.videogameapp.domain.entity.storeentity.StoreItemEntity
import com.squareup.picasso.Picasso

class StoreAdapter (private var listStore: List<StoreItemEntity>, private val listener: SetOnCLick): RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {
    interface SetOnCLick {
        fun onItemClicked(position: Int)
    }

    class StoreViewHolder(val binding: ItemStoreBinding): ViewHolder(binding.root)

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val data = listStore[position]
        holder.binding.apply {
            if (data.image.isNotBlank()) Picasso.get().load(data.image).apply{ fit(); into(ivStorePoster) }
            tvStoreTitle.text = data.name
            tvGameCountValue.text = data.gamesCount.toString()

            root.setOnClickListener{
                listener.onItemClicked(position)
            }
        }
    }

    fun getStoreId(position: Int): Long {
        return listStore[position].storeId
    }

    fun getStoreItem(position: Int): StoreItemEntity {
        return listStore[position]
    }

    fun updateList(newList: List<StoreItemEntity>) {
        listStore = newList
        notifyItemRangeChanged(0, listStore.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        return StoreViewHolder(ItemStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return listStore.size
    }


}