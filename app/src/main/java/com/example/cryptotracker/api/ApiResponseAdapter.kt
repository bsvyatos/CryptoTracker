package com.example.cryptotracker.api

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class ApiResponseAdapter<T>(private val responseType: Type) :
    CallAdapter<T, Call<ApiResponse<T>>> {
    override fun responseType() = responseType
    override fun adapt(call: Call<T>): Call<ApiResponse<T>> = ApiResponseResultCall(call)
}