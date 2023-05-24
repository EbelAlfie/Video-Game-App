package com.example.videogameapp.presentation.view.homeview

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.videogameapp.R
import com.example.videogameapp.Utils
import com.example.videogameapp.databinding.CardImageDialogBinding
import com.example.videogameapp.databinding.ItemScreenshotBinding
import com.example.videogameapp.domain.entity.gameentity.ScreenShotEntity
import com.squareup.picasso.Picasso

class GameScreenShotAdapter(private val listener: SetOnImageClickListener): RecyclerView.Adapter<GameScreenShotAdapter.ScreenShotViewHolder>() {
    private lateinit var context: Context
    interface SetOnImageClickListener {
        fun onImageClick(position: Int)
    }
    class DiffCallback : DiffUtil.ItemCallback<ScreenShotEntity>() {
        override fun areItemsTheSame(
            oldItem: ScreenShotEntity,
            newItem: ScreenShotEntity
        ): Boolean {
            return (oldItem.image == newItem.image)
        }
        override fun areContentsTheSame(
            oldItem: ScreenShotEntity,
            newItem: ScreenShotEntity
        ) = oldItem == newItem
    }

    private val diff = AsyncListDiffer(this, DiffCallback())

    class ScreenShotViewHolder(val binding: ItemScreenshotBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenShotViewHolder {
        context = parent.context
        return ScreenShotViewHolder(ItemScreenshotBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return diff.currentList.size
    }

    override fun onBindViewHolder(holder: ScreenShotViewHolder, position: Int) {
        val data = diff.currentList[position] ?: return
        holder.binding.apply {
            Picasso.get().load(data.image).apply {
                placeholder(Utils.createLoadingImage(context))
                error(R.drawable.baseline_broken_image_24)
                resize(600, 400)
                into(ivScreenshot)
            }
            root.setOnClickListener {
                listener.onImageClick(position)
            }
        }
    }

    fun zoomIn(position: Int, context: Context) {
        val data = diff.currentList[position] ?: return
        val cardImage = CardImageDialogBinding.inflate(LayoutInflater.from(context))
        Picasso.get().load(data.image).apply {
            error(R.drawable.baseline_broken_image_24)
            placeholder(Utils.createLoadingImage(context))
            into(cardImage.ivPreviewImage)
        }
        AlertDialog.Builder(context).apply {
            setView(cardImage.root)
            setCancelable(true)
        }.show()
    }

    fun updateData(newList: List<ScreenShotEntity>) {
        diff.submitList(newList)
    }
}