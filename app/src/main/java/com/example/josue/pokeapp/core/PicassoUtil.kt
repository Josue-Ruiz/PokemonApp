package com.example.josue.pokeapp.core

import android.widget.ImageView
import com.squareup.picasso.Picasso
import javax.inject.Inject

class PicassoUtil @Inject constructor(private val picasso: Picasso) {
    fun loadImage(url: String, imageview: ImageView) {
        picasso.load(url).into(imageview)
    }
}
