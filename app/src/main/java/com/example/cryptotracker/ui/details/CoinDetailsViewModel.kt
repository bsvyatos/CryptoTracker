package com.example.cryptotracker.ui.details

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import com.example.cryptotracker.models.CoinData
import com.example.cryptotracker.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class CoinDetailsViewModel @Inject constructor(private val coinRepository: CoinRepository) : ViewModel() {
    private val _coinData = MutableLiveData<CoinData?>(null)
    val coinData: LiveData<CoinData?> = _coinData

    fun setCoinDataId(id: String) {
        viewModelScope.launch {
            coinRepository.getCachedCoinDataById(id, viewModelScope).collectLatest {
                _coinData.value = it
            }
        }
    }
}