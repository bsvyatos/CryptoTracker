package com.example.cryptotracker

import android.app.Application
import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CryptoTrackerApplication: MultiDexApplication()