package com.example.cryptotracker.repository

import com.example.cryptotracker.api.ApiHelper
import com.example.cryptotracker.api.ApiResponse
import com.example.cryptotracker.db.CoinDao
import com.example.cryptotracker.models.CoinData
import com.example.cryptotracker.models.CoinListingsResponse
import com.example.cryptotracker.utils.NetworkBoundResource
import com.example.cryptotracker.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CoinRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val localDataSource: CoinDao) {

    fun getCoins(shouldFetch: Boolean = true): Flow<Resource<List<CoinData>>> {
        return object : NetworkBoundResource<List<CoinData>, CoinListingsResponse>() {
            override suspend fun saveFetchResult(data: CoinListingsResponse) {
                localDataSource.insertAll(data.data)
            }

            override suspend fun fetchFromApi(): ApiResponse<CoinListingsResponse> {
                return apiHelper.getCoinListings()
            }

            override fun fetchFromDb(): Flow<List<CoinData>> {
                return localDataSource.getAllCoinData()
            }

            override fun shouldFetch(data: List<CoinData>): Boolean {
                return shouldFetch
            }
        }.asFlow()
    }
}