package com.example.cryptotracker.api

import com.example.cryptotracker.models.CoinListingsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("cryptocurrency/listings/latest")
    suspend fun getCoinListings(): ApiResponse<CoinListingsResponse>
}