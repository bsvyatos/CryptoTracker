package com.example.cryptotracker.api

import com.example.cryptotracker.models.CoinListingsResponse
import com.example.cryptotracker.models.CoinMetaDataResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService): ApiHelper {
    override suspend fun getCoinListings(start: Int, limit: Int): ApiResponse<CoinListingsResponse> = apiService.getCoinListings(start, limit)
    override suspend fun getCoinsMetadata(ids: String): ApiResponse<CoinMetaDataResponse> = apiService.getCoinMetaData(ids)
    override suspend fun getCoinById(id: String): ApiResponse<CoinListingsResponse> = apiService.getCoinListingById(id)
}