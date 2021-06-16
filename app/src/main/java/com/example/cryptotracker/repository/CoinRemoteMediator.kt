package com.example.cryptotracker.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.cryptotracker.api.ApiEmptyResponse
import com.example.cryptotracker.api.ApiErrorResponse
import com.example.cryptotracker.api.ApiHelper
import com.example.cryptotracker.api.ApiSuccessResponse
import com.example.cryptotracker.db.AppDatabase
import com.example.cryptotracker.models.CoinData
import com.example.cryptotracker.utils.Utils
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@ExperimentalPagingApi
class CoinRemoteMediator @Inject constructor(
    private val networkService: ApiHelper,
    private val database: AppDatabase
) : RemoteMediator<Int, CoinData>() {
    private val coinDao = database.coinDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CoinData>
    ): MediatorResult {
        return try {
            // The network load method takes an optional after=<user.id>
            // parameter. For every page after the first, pass the last user
            // ID to let it continue from where it left off. For REFRESH,
            // pass null to load the first page.
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                // In this example, you never need to prepend, since REFRESH
                // will always load the first page in the list. Immediately
                // return, reporting end of pagination.
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    Utils.getNextPageValue(state.pages)
                }
            }

            // Suspending network load via Retrofit. This doesn't need to be
            // wrapped in a withContext(Dispatcher.IO) { ... } block since
            // Retrofit's Coroutine CallAdapter dispatches on a worker
            // thread.
            val pageSize = state.config.pageSize
            val response = networkService.getCoinListings(
                    start = loadKey, limit = pageSize
                )

            var endOfPagination = false

            when(response) {
                is ApiSuccessResponse -> {
                    database.withTransaction {
                        if(response.body.data.isEmpty()) {
                            endOfPagination = true
                        }

                        if (loadType == LoadType.REFRESH) {
                            coinDao.deleteCoins()
                        }

                        val data = response.body.data

                        // Get a list of coin ids in this format "id1, id2, ..., idn"
                        val ids = Utils.getCommaSeparatedIds(data)

//                        val ids: List<Int> = data.mapNotNull {
//                            it.id
//                        }

                        val metaDataResponse = networkService.getCoinsMetadata(ids)

                        // If metadata response is successful fill missing metadata specific
                        // variables like logos
                        if(metaDataResponse is ApiSuccessResponse) {
                            data.forEach { currData ->
                                metaDataResponse.body.data[currData.id.toString()]?.let { metaData ->
                                    currData.logo = metaData.logo
                                    currData.urls = metaData.urls
                                    currData.twitterUsername = metaData.twitterUsername
                                }
                            }
                        }

                        // Insert new users into database, which invalidates the
                        // current PagingData, allowing Paging to present the updates
                        // in the DB.
                        coinDao.insertAll(response.body.data)
                    }
                }

                is ApiEmptyResponse -> {
                    endOfPagination = true
                }

                is ApiErrorResponse -> {
                    return MediatorResult.Error(Throwable(response.errorMessage))
                }
            }

            MediatorResult.Success(
                endOfPaginationReached = endOfPagination
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}