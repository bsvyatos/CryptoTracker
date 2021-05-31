package com.example.cryptotracker.utils

data class Resource<ResultType>(var status: Status, var data: ResultType? = null, var message: String? = null) {

    companion object {
        /**
         * Creates [Resource] object with `SUCCESS` status and [data].
         */
        fun <ResultType> success(data: ResultType): Resource<ResultType> = Resource(Status.SUCCESS, data)

        /**
         * Creates [Resource] object with `CACHE` status to notify the UI
         * that only cached, potentially outdated, data is available and [data].
         */
       fun <ResultType> cache(data: ResultType): Resource<ResultType> = Resource(Status.CACHE, data)

        /**
         * Creates [Resource] object with `LOADING` status to notify
         * the UI to showing loading.
         */
        fun <ResultType> loading(): Resource<ResultType> = Resource(Status.LOADING)

        /**
         * Creates [Resource] object with `ERROR` status and [message] and potentially cached data.
         */
        fun <ResultType> error(message: String?, data: ResultType?): Resource<ResultType> = Resource(Status.ERROR, data, message)
    }

    enum class Status {
        SUCCESS,
        CACHE,
        ERROR,
        LOADING
    }
}