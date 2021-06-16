package com.example.cryptotracker.models

data class CoinListingsResponse (
    val data: List<CoinData>,
    val status: CoinDataStatus
)