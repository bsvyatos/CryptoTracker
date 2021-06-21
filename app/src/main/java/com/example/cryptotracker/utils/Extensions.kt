package com.example.cryptotracker.utils

fun Double.format(digits: Int) = "%.${digits}f".format(this)
fun Double?.divNull(div: Double?) = if(div == null || this == null) null else this / div