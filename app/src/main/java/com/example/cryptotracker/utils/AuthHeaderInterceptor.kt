package com.example.cryptotracker.utils

import okhttp3.Interceptor
import okhttp3.Response

class AuthHeaderInterceptor(val authKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeader("X-CMC_PRO_API_KEY", authKey).build()
        return chain.proceed(request);
    }
}