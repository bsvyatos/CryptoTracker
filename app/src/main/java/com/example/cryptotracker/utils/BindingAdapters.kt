package com.example.cryptotracker.utils

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptotracker.R
import com.example.cryptotracker.adapters.CoinsAdapter
import com.example.cryptotracker.models.CoinData
import com.squareup.picasso.Picasso

@BindingAdapter("imageUrl", requireAll = false)
fun loadImage(view: ImageView, url: String?) {
    if(url == null || url.isEmpty()) {
        view.setImageResource(R.drawable.ic_default_crypto_icon)
    } else {
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.ic_default_crypto_icon)
            .error(R.drawable.ic_default_crypto_icon)
            .into(view)
    }
}

@BindingAdapter(value = ["changeTxt", "changePeriod"], requireAll = false)
fun setChangeText(view: TextView, text: Double?, period: String) {
    text?.let {
        view.text = String.format(period, it.format(4))
    }
}
@BindingAdapter("doubleString")
fun setDoubleString(view: TextView, double: Double?) {
    double?.let {
        view.setText(it.format(2))
    }
}

@BindingAdapter("loadUrl")
fun loadUrl(view: WebView, url: String) {
    view.loadUrl(url)
}

@BindingAdapter("setWebViewClient")
fun setWebViewClient(view: WebView, client: WebViewClient) {
    view.webViewClient = client
}