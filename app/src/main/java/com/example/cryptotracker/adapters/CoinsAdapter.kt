package com.example.cryptotracker.adapters

import android.view.ViewGroup
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.cryptotracker.models.CoinData
import com.example.cryptotracker.ui.main.MainViewModel
import dagger.hilt.EntryPoint
import javax.inject.Inject

@ExperimentalPagingApi
class CoinsAdapter(val viewModel: MainViewModel) : PagingDataAdapter<CoinData, CoinViewHolder>(COIN_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        return CoinViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        getItem(position)?.let { coin ->
            holder.bind(coin, viewModel)

            holder.itemView.setOnClickListener {
                viewModel.navigateToDetailsEvent(coin.id.toString())
            }
        }
    }

    companion object {
        private val COIN_COMPARATOR = object : DiffUtil.ItemCallback<CoinData>() {
            override fun areItemsTheSame(oldItem: CoinData, newItem: CoinData): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: CoinData, newItem: CoinData): Boolean =
                oldItem == newItem
        }
    }
}