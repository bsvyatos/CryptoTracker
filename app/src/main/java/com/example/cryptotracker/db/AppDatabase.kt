package com.example.cryptotracker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cryptotracker.models.CoinData
import com.google.gson.Gson


@Database(entities = [CoinData::class], version = 1, exportSchema = false)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun coinDao(): CoinDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context, gson: Gson): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context, gson) }

        private fun buildDatabase(appContext: Context, gson: Gson) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "characters")
                .fallbackToDestructiveMigration()
                .addTypeConverter(RoomConverters(gson))
                .build()
    }
}