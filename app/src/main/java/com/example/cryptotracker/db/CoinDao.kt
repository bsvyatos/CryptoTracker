package com.example.cryptotracker.db

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.example.cryptotracker.models.CoinData
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {

    @Query("SELECT * FROM coins")
    fun getAllCoinData(): Flow<List<CoinData>>

    @Query("SELECT * FROM coins")
    fun getCoinPagingSource(): PagingSource<Int, CoinData>

    @Query("SELECT * FROM coins WHERE id = :id")
    fun getCoinDataById(id: Int): Flow<CoinData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<CoinData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoin(coin: CoinData)

    @Query("DELETE FROM coins")
    fun deleteCoins()
}