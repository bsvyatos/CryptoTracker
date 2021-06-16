package com.example.cryptotracker.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import com.example.cryptotracker.R
import com.example.cryptotracker.databinding.CoinDetailsFragmentBinding
import com.example.cryptotracker.models.CoinData
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagingApi
@AndroidEntryPoint
class CoinDetailsFragment : Fragment() {
    private val args: CoinDetailsFragmentArgs by navArgs()

    private val coinDetailsViewModel: CoinDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = CoinDetailsFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = coinDetailsViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData(args.coinId)
    }

    private fun getData(coinId: String) {
        coinDetailsViewModel.setCoinDataId(coinId)
    }

    private fun setData(coinData: CoinData) {
        val testView = view?.findViewById<TextView>(R.id.change_7d)
        testView?.setText(coinData.name)
    }
}