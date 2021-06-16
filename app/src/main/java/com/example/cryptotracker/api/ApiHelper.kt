package com.example.cryptotracker.api

import com.example.cryptotracker.models.CoinListingsResponse
import com.example.cryptotracker.models.CoinMetaDataResponse
import com.example.cryptotracker.models.CoinQuotesResponse

interface ApiHelper {
    suspend fun getCoinListings(start: Int = 1, limit: Int = 10): ApiResponse<CoinListingsResponse>
    suspend fun getCoinById(id: String): ApiResponse<CoinQuotesResponse>
    suspend fun getCoinsMetadata(ids: String): ApiResponse<CoinMetaDataResponse>
}