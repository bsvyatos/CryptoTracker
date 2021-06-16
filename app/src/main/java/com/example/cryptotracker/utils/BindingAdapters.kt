package com.example.cryptotracker.utils

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.cryptotracker.R


@BindingAdapter("imageUrl", requireAll = false)
fun loadImage(view: ImageView, url: String?) {
    Glide.with(view.context)
        .load(url ?: "")
        .placeholder(R.drawable.ic_default_crypto_icon)
        .error(R.drawable.ic_default_crypto_icon)
        .into(view)
}

@BindingAdapter(value = ["changeTxt", "changePeriod"], requireAll = false)
fun setChangeText(view: TextView, text: Double?, period: String) {
    text?.let {
        view.text = String.format(period, it.toString())
    }
}