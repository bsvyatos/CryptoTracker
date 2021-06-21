package com.example.cryptotracker.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.cryptotracker.utils.divNull
import com.google.gson.annotations.SerializedName

@Entity(tableName = "coins")
data class CoinData(
    @PrimaryKey
    val id: Int,
    @SerializedName("circulating_supply")
    val circulatingSupply: Double? = null,
    @SerializedName("cmc_rank")
    val cmcRank: Double? = null,
    @SerializedName("date_added")
    val dateAdded: String? = null,
    @SerializedName("last_updated")
    val lastUpdated: String? = null,
    @SerializedName("max_supply")
    val maxSupply: Double? = null,
    val name: String? = null,
    @SerializedName("num_market_pairs")
    val numMarketPairs: Double? = null,
    val quote: Quote? = null,
    val slug: String? = null,
    val symbol: String? = null,
    val tags: List<String>? = null,
    @SerializedName("total_supply")
    val totalSupply: Double? = null,
    var logo: String? = null,
    var urls: Urls? = null,
    var twitterUsername: String? = null,
) {
    val volumeCap: Double?
        get() = this.quote?.dollar?.volume_24h.divNull(this.quote?.dollar?.market_cap)

}
