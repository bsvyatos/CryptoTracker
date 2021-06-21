package com.example.cryptotracker.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.cryptotracker.api.ApiHelper
import com.example.cryptotracker.db.AppDatabase
import com.example.cryptotracker.models.CoinData
import com.example.cryptotracker.models.CoinsSortingTypes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class CoinRepository @Inject constructor(
    private val networkService: ApiHelper,
    private val database: AppDatabase
) {

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }

    fun getCoinsStream(sortType: CoinsSortingTypes, ifReload: Boolean): Flow<PagingData<CoinData>> {
        val pagingSourceFactory = { database.coinDao().getCoinPagingSource() }

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = NETWORK_PAGE_SIZE * 2
            ),
            remoteMediator = CoinRemoteMediator(networkService, database, sortType, ifReload),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun getCachedCoinDataById(id: String): Flow<CoinData> {
        return database.coinDao().getCoinDataById(id.toInt())
    }
}