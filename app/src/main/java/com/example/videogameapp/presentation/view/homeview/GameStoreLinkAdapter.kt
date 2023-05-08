package com.example.videogameapp.presentation.view.homeview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.videogameapp.databinding.ItemStoreLinkBinding
import com.example.videogameapp.domain.entity.gameentity.StoreEntity

class GameStoreLinkAdapter(private var storeList: MutableList<StoreEntity>, private val listener: SetOnItemClickListener): RecyclerView.Adapter<GameStoreLinkAdapter.StoreViewHolder>() {
    interface SetOnItemClickListener {
        fun onStoreClicked(position: Int)
    }

    class StoreViewHolder(val binding: ItemStoreLinkBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        return StoreViewHolder(ItemStoreLinkBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return storeList.size
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val data = storeList[position]
        holder.binding.apply {
            btnStoreLink.text = data.name
            root.setOnClickListener {
                listener.onStoreClicked(position)
            }
        }
    }

    fun getUrl(position: Int): String {
        return storeList[position].url
    }

    fun addData(newStoreList: MutableList<StoreEntity>) {
        storeList.addAll(newStoreList)
        notifyItemRangeChanged(0, storeList.size)
    }
}