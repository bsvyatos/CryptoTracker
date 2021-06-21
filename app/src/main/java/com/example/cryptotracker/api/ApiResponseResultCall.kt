package com.example.cryptotracker.api

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiResponseResultCall<T>(private val delegate: Call<T>) : Call<ApiResponse<T>> {
    override fun enqueue(callback: Callback<ApiResponse<T>>) = delegate.enqueue(
        object : Callback<T> {

            override fun onResponse(call: Call<T>, response: Response<T>) {
                callback.onResponse(
                    this@ApiResponseResultCall,
                    Response.success(ApiResponse.create(response))
                )
            }

            override fun onFailure(call: Call<T>, throwable: Throwable) {
                callback.onResponse(
                    this@ApiResponseResultCall,
                    Response.success(ApiResponse.create<T>(throwable))
                )
            }
        }
    )

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun clone(): Call<ApiResponse<T>> = ApiResponseResultCall<T>(delegate.clone())

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<ApiResponse<T>> = throw UnsupportedOperationException()

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}