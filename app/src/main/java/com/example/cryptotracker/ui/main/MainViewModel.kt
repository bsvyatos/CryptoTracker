package com.example.cryptotracker.ui.main

import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.cryptotracker.models.CoinData
import com.example.cryptotracker.repository.CoinRepository
import com.example.cryptotracker.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class MainViewModel @Inject constructor(coinRepository: CoinRepository) : ViewModel() {
    private val coinListings = coinRepository.getCoinsStream().cachedIn(viewModelScope)
    private val _refreshDataEvent = MutableLiveData<Event<Unit>>()
    val refreshDataEvent: LiveData<Event<Unit>> = _refreshDataEvent
    private val _navigateToDetailsEvent = MutableLiveData<Event<String>>()
    val navigateToDetailsEvent: LiveData<Event<String>> = _navigateToDetailsEvent

    fun getCoins() = coinListings

    fun refresh() {
        _refreshDataEvent.value = Event(Unit)
    }

    fun navigateToDetailsEvent(id: String) {
        _navigateToDetailsEvent.value = Event(id)
    }




}