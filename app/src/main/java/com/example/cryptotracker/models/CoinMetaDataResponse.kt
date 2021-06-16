package com.example.cryptotracker.models

data class CoinMetaDataResponse(
    val data: HashMap<String, CoinMetadata>,
    val status: CoinMetaDataStatus
)