package com.example.cryptotracker.ui.main

import android.view.MenuItem
import android.view.View
import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.cryptotracker.R
import com.example.cryptotracker.models.CoinData
import com.example.cryptotracker.models.CoinsSortingTypes
import com.example.cryptotracker.repository.CoinRepository
import com.example.cryptotracker.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class MainViewModel @Inject constructor(val coinRepository: CoinRepository) : ViewModel() {
    private val _newPagingDataEvent = MutableLiveData<Event<Flow<PagingData<CoinData>>>>()
    val newPagingDataEvent: LiveData<Event<Flow<PagingData<CoinData>>>> = _newPagingDataEvent
    private val _refreshDataEvent = MutableLiveData<Event<Unit>>()
    val refreshDataEvent: LiveData<Event<Unit>> = _refreshDataEvent
    private val _navigateToDetailsEvent = MutableLiveData<Event<String>>()
    private val _retryDataEvent = MutableLiveData<Event<Unit>>()
    val retryDataEvent: LiveData<Event<Unit>> = _retryDataEvent
    val navigateToDetailsEvent: LiveData<Event<String>> = _navigateToDetailsEvent
    private var latestCoinFlow: Flow<PagingData<CoinData>>? = null
    var coinsSort = CoinsSortingTypes.MARKET_CAP
        private set

    fun getLatestData() {
        latestCoinFlow?.let {
            _newPagingDataEvent.value = Event(it)
            return
        }

        getCoins()
    }

    private fun getCoins(ifReload: Boolean = false) {
        coinRepository.getCoinsStream(coinsSort, ifReload).cachedIn(viewModelScope).let {
            latestCoinFlow = it
            _newPagingDataEvent.value = Event(it)
        }
    }

    val retry = View.OnClickListener {
        _retryDataEvent.value = Event(Unit)
    }

    fun refresh() {
        _refreshDataEvent.value = Event(Unit)
    }

    fun navigateToDetailsEvent(id: String) {
        _navigateToDetailsEvent.value = Event(id)
    }

    fun changeSorting(menuItem: MenuItem) {
        val newSort = when(menuItem.itemId) {
            R.id.market_cap -> CoinsSortingTypes.MARKET_CAP
            R.id.volume -> CoinsSortingTypes.VOLUME
            R.id.new_coins -> CoinsSortingTypes.NEW_COINS
            else -> return
        }

        if (newSort != coinsSort) {
            coinsSort = newSort
            getCoins(true)
        }
    }
}

