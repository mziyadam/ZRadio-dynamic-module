package com.ziyad.core.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

object Utils {
    fun ImageView.loadImage(url: String?) {
        Glide.with(this.context)
            .load(url)
            .circleCrop()
            .into(this)
    }
}