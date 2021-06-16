package com.example.cryptotracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptotracker.R

class CoinLoadStateViewHolder(
    itemView: View,
    retry: () -> Unit
): RecyclerView.ViewHolder(itemView) {
    var progressBar: ProgressBar? = null
    var retryButton: Button? = null
    var errorMsg: TextView? = null

    init {
        progressBar = itemView.findViewById(R.id.progress_bar)
        retryButton = itemView.findViewById(R.id.retry_button)
        errorMsg = itemView.findViewById(R.id.error_msg)
        retryButton?.setOnClickListener {
            retry.invoke()
        }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            errorMsg?.text = loadState.error.localizedMessage
        }
        progressBar?.isVisible = loadState is LoadState.Loading
        retryButton?.isVisible = loadState is LoadState.Error
        errorMsg?.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): CoinLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.loading_footer_item, parent, false)
            return CoinLoadStateViewHolder(view, retry)
        }
    }
}