package com.example.cryptotracker.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.cryptotracker.api.ApiHelper
import com.example.cryptotracker.api.ApiResponse
import com.example.cryptotracker.db.AppDatabase
import com.example.cryptotracker.db.CoinDao
import com.example.cryptotracker.models.CoinData
import com.example.cryptotracker.models.CoinListingsResponse
import com.example.cryptotracker.utils.NetworkBoundResource
import com.example.cryptotracker.utils.Resource
import androidx.lifecycle.lifecycleScope
import com.example.cryptotracker.api.ApiSuccessResponse
import com.example.cryptotracker.models.CoinsSortingTypes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ExperimentalPagingApi
class CoinRepository @Inject constructor(
    private val networkService: ApiHelper,
    private val database: AppDatabase) {

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }

    fun getCoinsStream(sortType: CoinsSortingTypes, ifReload: Boolean): Flow<PagingData<CoinData>> {
        val pagingSourceFactory = { database.coinDao().getCoinPagingSource() }

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = NETWORK_PAGE_SIZE),
            remoteMediator = CoinRemoteMediator(networkService, database, sortType, ifReload),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun getCachedCoinDataById(id: String, scope: CoroutineScope): Flow<CoinData> {
        val returnFlow = database.coinDao().getCoinDataById(id.toInt())

        // Get CoinData from db
        scope.launch(Dispatchers.IO) {
            val result = networkService.getCoinById(id)

            // Refresh the data in background just in case
            if(result is ApiSuccessResponse) {
                result.body.data[id]?.let {
                    database.coinDao().insertCoin(it)
                }
            }
        }

        return returnFlow
    }
}