package com.example.cryptotracker.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.cryptotracker.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val coinRepository: CoinRepository) : ViewModel() {
    private val coinListings = coinRepository.getCoins().asLiveData()

    fun getCoins() = coinListings
}