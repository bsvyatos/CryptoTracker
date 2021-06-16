package com.example.cryptotracker.models

import com.google.gson.annotations.SerializedName

data class CoinMetadata(
    val category: String?,
    @SerializedName("date_added")
    val dateAdded: String?,
    val description: String?,
    val id: Int,
    @SerializedName("is_hidden")
    val isHidden: Int?,
    val logo: String?,
    val name: String?,
    val notice: String?,
    val slug: String?,
    val subreddit: String?,
    val symbol: String?,
    @SerializedName("tag-groups")
    val tagGroups: List<String>?,
    @SerializedName("tag-names")
    val tagNames: List<String>?,
    val tags: List<String>?,
    @SerializedName("twitter_username")
    val twitterUsername: String?,
    val urls: Urls?
)