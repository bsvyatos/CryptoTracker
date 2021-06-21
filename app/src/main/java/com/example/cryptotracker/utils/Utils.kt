package com.example.cryptotracker.utils

import android.animation.ArgbEvaluator
import android.graphics.Color
import androidx.paging.PagingSource
import com.example.cryptotracker.models.CoinData
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

            // The function will work poorly if we exceed max color
            val finalValue: Float = if(abs(value) > maxColor) maxColor.toFloat() else value
            return ArgbEvaluator().evaluate(abs(finalValue*100/maxColor), Color.GRAY, color) as Int
        }
    }
}