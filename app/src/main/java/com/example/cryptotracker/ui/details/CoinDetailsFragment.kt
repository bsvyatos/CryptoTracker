package com.example.cryptotracker.ui.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import com.example.cryptotracker.R
import com.example.cryptotracker.models.CoinData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@AndroidEntryPoint
class CoinDetailsFragment : Fragment() {
    private val args: CoinDetailsFragmentArgs by navArgs()

    private val viewModel: CoinDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.coin_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData(args.coinId)
    }

    private fun getData(coinId: String) {
        viewModel.setCoinDataId(coinId)
    }

    private fun setData(coinData: CoinData) {
        val testView = view?.findViewById<TextView>(R.id.change_7d)
        testView?.setText(coinData.name)
    }

}