package com.example.cryptotracker.api

import com.example.cryptotracker.models.CoinListingsResponse
import com.example.cryptotracker.models.CoinMetaDataResponse

interface ApiHelper {
    suspend fun getCoinListings(start: Int = 1, limit: Int = 10): ApiResponse<CoinListingsResponse>
    suspend fun getCoinById(id: String): ApiResponse<CoinListingsResponse>
    suspend fun getCoinsMetadata(ids: String): ApiResponse<CoinMetaDataResponse>
}