package com.example.cryptotracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptotracker.R
import com.example.cryptotracker.databinding.CoinMainItemBinding
import com.example.cryptotracker.models.CoinData
import com.example.cryptotracker.ui.main.MainViewModel
import com.example.cryptotracker.utils.ColoredChangeTextView

class CoinViewHolder private constructor(val binding: CoinMainItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var coinData: CoinData

    @ExperimentalPagingApi
    fun bind(_coinData: CoinData, viewModel: MainViewModel) {
        coinData = _coinData
        binding.coinData = _coinData
        binding.viewModel = viewModel
    }

    companion object {
        fun create(parent: ViewGroup): CoinViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = CoinMainItemBinding.inflate(inflater, parent, false)
            return CoinViewHolder(binding)
        }
    }
}
