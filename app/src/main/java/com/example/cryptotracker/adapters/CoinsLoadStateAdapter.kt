package com.example.cryptotracker.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class CoinsLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<CoinLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: CoinLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): CoinLoadStateViewHolder {
        return CoinLoadStateViewHolder.create(parent, retry)
    }
}