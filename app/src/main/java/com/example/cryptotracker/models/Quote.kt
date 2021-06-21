package com.example.cryptotracker.models

import com.google.gson.annotations.SerializedName

data class Quote(
    @SerializedName("USD")
    val dollar: Dollar
)