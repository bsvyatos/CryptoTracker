package com.example.cryptotracker.di

import android.content.Context
import android.provider.SyncStateContract
import com.example.cryptotracker.BuildConfig
import com.example.cryptotracker.api.ApiHelper
import com.example.cryptotracker.api.ApiHelperImpl
import com.example.cryptotracker.api.ApiResponseAdapterFactory
import com.example.cryptotracker.api.ApiService
import com.example.cryptotracker.db.AppDatabase
import com.example.cryptotracker.utils.AuthHeaderInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()

    @Provides
    @Singleton
    fun provideAuthHeaderInterceptor(): AuthHeaderInterceptor = AuthHeaderInterceptor(BuildConfig.COINMARKETCAP_KEY)

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor, authHeaderInterceptor: AuthHeaderInterceptor) = if(BuildConfig.DEBUG) {
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authHeaderInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
        .addCallAdapterFactory(ApiResponseAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext appContext: Context, gson: Gson) = AppDatabase.getDatabase(appContext, gson)

    @Singleton
    @Provides
    fun provideCoinDao(db: AppDatabase) = db.coinDao()
}