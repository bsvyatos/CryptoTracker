package com.example.cryptotracker.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coins")
data class CoinData(
    val circulating_supply: Int,
    val cmc_rank: Int,
    val date_added: String,
    @PrimaryKey
    val id: Int,
    val last_updated: String,
    val max_supply: Int,
    val name: String,
    val num_market_pairs: Int,
    val platform: Any,
    val quote: Quote,
    val slug: String,
    val symbol: String,
    val tags: List<String>,
    val total_supply: Int
)