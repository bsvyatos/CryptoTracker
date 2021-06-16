package com.example.cryptotracker.models

data class CoinQuotesResponse(
    val data: HashMap<String, CoinData>,
    val status: CoinDataStatus
)
