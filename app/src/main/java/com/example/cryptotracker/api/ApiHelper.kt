package com.example.cryptotracker.api

import com.example.cryptotracker.models.CoinListingsResponse
import kotlinx.coroutines.flow.Flow

interface ApiHelper {
    suspend fun getCoinListings(): ApiResponse<CoinListingsResponse>
}