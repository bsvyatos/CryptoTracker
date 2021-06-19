package com.example.cryptotracker.ui.details

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import com.example.cryptotracker.models.CoinData
import com.example.cryptotracker.repository.CoinRepository
import com.example.cryptotracker.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class CoinDetailsViewModel @Inject constructor(private val coinRepository: CoinRepository) : ViewModel() {
    private val _coinData = MutableLiveData<CoinData?>(null)
    val coinData: LiveData<CoinData?> = _coinData
    private val _navigateToUrl = MutableLiveData<Event<String>>()
    val navigateToUrl: LiveData<Event<String>> = _navigateToUrl

    fun setCoinDataId(id: String) {
        viewModelScope.launch {
            coinRepository.getCachedCoinDataById(id, viewModelScope).collectLatest {
                _coinData.value = it
            }
        }
    }

    val twitterClick = View.OnClickListener {
        coinData.value?.urls?.twitter?.let {
            if(it.isNotEmpty()) {
                _navigateToUrl.value = Event(it[0])
            }
        }
    }

    val websiteClick = View.OnClickListener {
        coinData.value?.urls?.website?.let {
            if(it.isNotEmpty()) {
                _navigateToUrl.value = Event(it[0])
            }
        }
    }
}