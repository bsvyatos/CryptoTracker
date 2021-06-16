package com.example.cryptotracker.api

import com.example.cryptotracker.models.CoinListingsResponse
import com.example.cryptotracker.models.CoinMetaDataResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("cryptocurrency/listings/latest")
    suspend fun getCoinListingById(@Query("sort") sort: String): ApiResponse<CoinListingsResponse>

    @GET("cryptocurrency/listings/latest")
    suspend fun getCoinListings(@Query("start") start: Int? = 1, @Query("limit") limit: Int = 10): ApiResponse<CoinListingsResponse>

    @GET("cryptocurrency/info")
    suspend fun getCoinMetaData(@Query("id") ids: List<Int>): ApiResponse<CoinMetaDataResponse>
}