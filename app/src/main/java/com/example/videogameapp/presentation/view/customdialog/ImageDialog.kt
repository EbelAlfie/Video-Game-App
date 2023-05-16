package com.example.videogameapp.presentation.view.customdialog

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialog
import com.example.videogameapp.databinding.CardImageDialogBinding
import com.squareup.picasso.Picasso

class ImageDialog (context: Context, private var imageUrl: String) : AppCompatDialog(context) {

    private lateinit var binding: CardImageDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = CardImageDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Picasso.get().load(imageUrl).into(binding.ivPreviewImage)
    }
}