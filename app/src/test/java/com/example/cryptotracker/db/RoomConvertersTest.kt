package com.example.cryptotracker.db

import com.example.cryptotracker.models.CoinMetadata
import com.google.gson.Gson
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test

class RoomConvertersTest : TestCase() {
    lateinit var roomConverters: RoomConverters

    @Before
    fun setup() {
        roomConverters = RoomConverters(Gson())
    }

    @Test
    fun testMetaDataConversion() {
//        val coinMetadata = CoinMetadata()
//        roomConverters.coinMetaDataToString()
    }
}