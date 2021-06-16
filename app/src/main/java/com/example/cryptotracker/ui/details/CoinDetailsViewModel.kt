package com.example.cryptotracker.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.example.cryptotracker.models.CoinData
import com.example.cryptotracker.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class CoinDetailsViewModel @Inject constructor(private val coinRepository: CoinRepository) : ViewModel() {
    private var _coinData = MutableStateFlow<CoinData?>(null)
    val coinData: StateFlow<CoinData?> = _coinData

    fun setCoinDataId(id: String) {
        viewModelScope.launch {
            coinRepository.getCachedCoinDataById(id, viewModelScope).collectLatest {
                _coinData.value = it
            }
        }
    }
}