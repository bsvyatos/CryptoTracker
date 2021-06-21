package com.example.cryptotracker.api

import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiResponseAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *> {
        check(returnType is ParameterizedType) { "$returnType must be parameterized. Raw types are not supported" }

        val containerType = getParameterUpperBound(0, returnType)
        if (getRawType(containerType) != ApiResponse::class.java) {
            throw IllegalArgumentException("type must be a resource")
        }

        if (containerType !is ParameterizedType) {
            throw IllegalArgumentException("resource must be parameterized")
        }
        val bodyType = getParameterUpperBound(0, containerType)
        return ApiResponseAdapter<Any>(bodyType)
    }
}