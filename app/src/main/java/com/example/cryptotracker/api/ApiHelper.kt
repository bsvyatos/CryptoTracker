package com.example.cryptotracker.api

import com.example.cryptotracker.models.CoinListingsResponse
import com.example.cryptotracker.models.CoinMetaDataResponse
import kotlinx.coroutines.flow.Flow

interface ApiHelper {
    suspend fun getCoinListings(start: Int = 1, limit: Int = 10): ApiResponse<CoinListingsResponse>
    suspend fun getCoinById(id: String): ApiResponse<CoinListingsResponse>
    suspend fun getCoinsMetadata(ids: List<Int>): ApiResponse<CoinMetaDataResponse>
}