package com.example.cryptotracker.api

import com.example.cryptotracker.models.CoinListingsResponse
import com.example.cryptotracker.models.CoinMetaDataResponse
import com.example.cryptotracker.models.CoinQuotesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("cryptocurrency/quotes/latest")
    suspend fun getCoinListingById(@Query("id") id: String): ApiResponse<CoinQuotesResponse>

    @GET("cryptocurrency/listings/latest")
    suspend fun getCoinListings(
        @Query("start") start: Int?,
        @Query("limit") limit: Int,
        @Query("sort") sort: String,
        @Query("sort_dir") sortDir: String
    ): ApiResponse<CoinListingsResponse>

    @GET("cryptocurrency/info")
    suspend fun getCoinMetaData(
        @Query(
            "id",
            encoded = true
        ) ids: String
    ): ApiResponse<CoinMetaDataResponse>
}