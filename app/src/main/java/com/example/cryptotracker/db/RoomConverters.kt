package com.example.cryptotracker.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.cryptotracker.models.Quote
import com.example.cryptotracker.models.Urls
import com.google.gson.Gson
import javax.inject.Inject

@ProvidedTypeConverter
class RoomConverters @Inject constructor(private val gson: Gson) {
    @TypeConverter
    fun urlsToString(urls: Urls?): String {
        return gson.toJson(urls)
    }

    @TypeConverter
    fun stringToUrls(urls: String): Urls? {
        return gson.fromJson(urls, Urls::class.java)
    }

    @TypeConverter
    fun quoteToString(quote: Quote?): String {
        return gson.toJson(quote)
    }

    @TypeConverter
    fun stringToQuote(quote: String): Quote? {
        return gson.fromJson(quote, Quote::class.java)
    }

    @TypeConverter
    fun listOfStringsToString(list: List<String>?): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun stringToListOfStrings(list: String): List<String>? {
        return gson.fromJson(list, Array<String>::class.java).asList()
    }
}