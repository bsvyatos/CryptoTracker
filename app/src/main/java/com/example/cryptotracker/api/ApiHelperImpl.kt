package com.example.cryptotracker.api

import com.example.cryptotracker.models.CoinListingsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService): ApiHelper {
    override suspend fun getCoinListings(): ApiResponse<CoinListingsResponse> = apiService.getCoinListings()
}