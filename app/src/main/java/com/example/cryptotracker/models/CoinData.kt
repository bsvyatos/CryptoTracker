package com.example.cryptotracker.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "coins")
data class CoinData(
    @PrimaryKey
    val id: Int,
    @SerializedName("circulating_supply")
    val circulatingSupply: Double?,
    @SerializedName("cmc_rank")
    val cmcRank: Double?,
    @SerializedName("date_added")
    val dateAdded: String?,
    @SerializedName("last_updated")
    val lastUpdated: String?,
    @SerializedName("max_supply")
    val maxSupply: Double?,
    val name: String?,
    @SerializedName("num_market_pairs")
    val numMarketPairs: Double?,
    val quote: Quote?,
    val slug: String?,
    val symbol: String?,
    val tags: List<String>?,
    @SerializedName("total_supply")
    val totalSupply: Double?,
    var logo: String?,
    var urls: Urls?,
    var twitterUsername: String?
)