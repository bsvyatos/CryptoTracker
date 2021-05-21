package com.example.cryptotracker

import android.app.Application
import com.example.cryptotracker.di.DaggerAppComponent
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CryptoTrackerApplication: Application()