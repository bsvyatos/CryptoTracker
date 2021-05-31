package com.example.cryptotracker.utils

import com.example.cryptotracker.api.ApiEmptyResponse
import com.example.cryptotracker.api.ApiErrorResponse
import com.example.cryptotracker.api.ApiResponse
import com.example.cryptotracker.api.ApiSuccessResponse
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType> {

    fun asFlow() = flow {
        val data = fetchFromDb().first()

        val flow = if (shouldFetch(data)) {
            emit(Resource.loading())

            when(val response = fetchFromApi()) {
                is ApiSuccessResponse -> {
                    saveFetchResult(response.body)
                    fetchFromDb().map { Resource.success(it) }
                }

                is ApiEmptyResponse -> {
                    fetchFromDb().map { Resource.cache(it) }
                }

                is ApiErrorResponse -> {
                    fetchFromDb().map { Resource.error(response.errorMessage, it) }
                }
            }
        } else {
            fetchFromDb().map { Resource.success(it) }
        }

        emitAll(flow)
    }

    open fun shouldFetch(data: ResultType): Boolean = true
    abstract suspend fun saveFetchResult(data: RequestType)
    abstract suspend fun fetchFromApi(): ApiResponse<RequestType>
    abstract fun fetchFromDb(): Flow<ResultType>
}