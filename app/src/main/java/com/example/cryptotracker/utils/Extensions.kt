package com.example.cryptotracker.utils

fun Double.format(digits: Int) = "%.${digits}f".format(this)
