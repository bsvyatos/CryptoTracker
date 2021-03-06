package com.example.cryptotracker.api

import com.example.cryptotracker.models.CoinListingsResponse
import com.example.cryptotracker.models.CoinMetaDataResponse
import com.example.cryptotracker.models.CoinQuotesResponse
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override suspend fun getCoinListings(
        start: Int,
        limit: Int,
        sort: String,
        sortDir: String
    ): ApiResponse<CoinListingsResponse> = apiService.getCoinListings(start, limit, sort, sortDir)

    override suspend fun getCoinsMetadata(ids: String): ApiResponse<CoinMetaDataResponse> =
        apiService.getCoinMetaData(ids)

    override suspend fun getCoinById(id: String): ApiResponse<CoinQuotesResponse> =
        apiService.getCoinListingById(id)
}