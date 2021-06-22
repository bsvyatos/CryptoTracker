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
import com.example.cryptotracker.models.CoinsSortingTypes
import com.example.cryptotracker.utils.Utils
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@ExperimentalPagingApi
class CoinRemoteMediator @Inject constructor(
    private val networkService: ApiHelper,
    private val database: AppDatabase,
    private val coinSort: CoinsSortingTypes,
    private val ifReload: Boolean
) : RemoteMediator<Int, CoinData>() {
    private val coinDao = database.coinDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CoinData>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                // Start with first index again if refresh
                LoadType.REFRESH -> 1
                // We are not going to prepend pages thus return success and end of pagination flag
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                // Simply increment the number of our coin rows to get the proper append index
                LoadType.APPEND -> {
                    coinDao.getCoinRowCount() + 1
                }
            }

            val pageSize = state.config.pageSize
            val sort = when (coinSort) {
                CoinsSortingTypes.MARKET_CAP -> Pair("market_cap", "desc")
                CoinsSortingTypes.VOLUME -> Pair("volume_30d", "desc")
                CoinsSortingTypes.NEW_COINS -> Pair("date_added", "desc")
            }

            val response = networkService.getCoinListings(
                start = loadKey, limit = pageSize, sort = sort.first, sortDir = sort.second
            )

            var endOfPagination = false

            when (response) {
                is ApiSuccessResponse -> {
                    database.withTransaction {
                        // Remove current items in the db if we're refreshing
                        if (loadType == LoadType.REFRESH || ifReload) {
                            coinDao.deleteCoins()
                        }

                        val data = response.body.data

                        // Get a list of coin ids in this format "id1, id2, ..., idn"
                        val ids = Utils.getCommaSeparatedIds(data)
                        val metaDataResponse = networkService.getCoinsMetadata(ids)

                        // If metadata response is successful fill missing metadata specific
                        // variables like logos
                        if (metaDataResponse is ApiSuccessResponse) {
                            data.forEach { currData ->
                                metaDataResponse.body.data[currData.id.toString()]?.let { metaData ->
                                    currData.logo = metaData.logo
                                    currData.urls = metaData.urls
                                    currData.twitterUsername = metaData.twitterUsername
                                }
                            }
                        }

                        // Room is the source of truth in this approach, thus new data is just
                        // inserted inside it
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