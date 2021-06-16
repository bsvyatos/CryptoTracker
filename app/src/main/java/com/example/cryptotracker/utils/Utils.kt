package com.example.cryptotracker.utils

import android.animation.ArgbEvaluator
import android.graphics.Color
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cryptotracker.models.CoinData
import com.example.cryptotracker.repository.CoinRepository
import kotlin.math.abs

class Utils {
    companion object {
        fun getFloat(str: CharSequence): Float? {
            val regex = "[-+]?\\d*\\.\\d+|\\d+".toRegex()
            return regex.find(str)?.value?.toFloat()
        }

        fun getCommaSeparatedIds(data: List<CoinData>): String {
            return data.map {
                it.id.toString()
            }.reduce { a, b -> "$a,$b"}
        }

        fun getChangeTextColor(minusColor: Int, plusColor: Int, maxColor: Int, value: Float): Int {
            val color: Int = if(value > 0) {
                plusColor
            } else {
                minusColor
            }

            return ArgbEvaluator().evaluate(abs(value*100/maxColor), Color.GRAY, color) as Int
        }

        fun getNextPageValue(pages: List<PagingSource.LoadResult.Page<*, *>>): Int {
            return pages.map { page ->
                page.data.size
            }.sum() + 1
        }
    }
}